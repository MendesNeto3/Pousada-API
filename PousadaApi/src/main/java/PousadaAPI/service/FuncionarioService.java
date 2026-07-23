package PousadaAPI.service;

import PousadaAPI.domain.enums.Role;
import PousadaAPI.domain.exception.RegistroDuplicadoException;
import PousadaAPI.domain.mapper.FuncionarioMapper;
import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.domain.model.Usuario;
import PousadaAPI.dto.request.CriarFuncionarioRequestDTO;
import PousadaAPI.dto.response.FuncionarioResponse;
import PousadaAPI.repository.FuncionarioRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Data
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final FuncionarioMapper mapper;

    public FuncionarioResponse cadastrarFuncionario (CriarFuncionarioRequestDTO dto) {
        if (funcionarioRepository.existsByEmail(dto.email())) {
            throw new RegistroDuplicadoException("Já existe um funcionário com este email");
        }
        if (funcionarioRepository.existsByNome(dto.nome())) {
            throw new RegistroDuplicadoException("Este respectivo nome já está em uso");
        }
        Usuario usuario = Usuario.builder()
                .login(dto.login())
                .senha(passwordEncoder.encode(dto.senha()))
                .role(Role.FUNCIONARIO)
                .ativo(true)
                .bloqueado(false)
                .build();

        Funcionario funcionario = Funcionario.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .cargo(dto.cargo())
                .email(dto.email())
                .usuario(usuario)
                .build();

        return mapper.toResponse(funcionarioRepository.save(funcionario));
    }

    public FuncionarioResponse deletarFuncionario (String id) {
        var funcionarioId = UUID.fromString(id);
        Funcionario funcionario = funcionarioRepository
                .findById(funcionarioId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Funcionário não existente."));
         funcionarioRepository.delete(funcionario);
         return mapper.toResponse(funcionario);
    }

    public List<Funcionario> listarFuncionario(String email, String nome) {
        var funcionario1 = new Funcionario();
        funcionario1.setNome(nome);
        funcionario1.setEmail(email);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Funcionario> example = Example.of(funcionario1, matcher);

        return funcionarioRepository.findAll(example);
    }

    public FuncionarioResponse atualizarDadosFuncionario (CriarFuncionarioRequestDTO dto, String  id) {
       return funcionarioRepository
               .findById(UUID.fromString(id))
               .map(f -> {
                   f.setSenha(dto.senha());
                   f.setNome(dto.nome());
                   f.setEmail(dto.email());
                   f.setCargo(dto.cargo());
                   return funcionarioRepository.save(f);
               })
               .map(mapper::toResponse)
               .orElseThrow(()
                       -> new UsernameNotFoundException("Usuário não enconrtado."));
    }
}
