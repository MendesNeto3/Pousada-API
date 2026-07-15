package PousadaAPI.service;

import PousadaAPI.domain.exception.RegistroDuplicadoException;
import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.request.CriarFuncionarioRequestDTO;
import PousadaAPI.repository.FuncionarioRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private PasswordEncoder encoder;
    private  ResponseMapper mapper;

    public Funcionario cadastrarFuncionario (CriarFuncionarioRequestDTO dto) {
        if (funcionarioRepository.existsByEmail(dto.email())) {
            throw new RegistroDuplicadoException("Já existe um funcionário com este email");
        }
        if (funcionarioRepository.existsByName(dto.senha())) {
            throw new RegistroDuplicadoException("Este respectivo nome já está em uso");
        }
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(dto.email());
        String senha = encoder.encode(dto.senha());
        funcionario.setSenha(senha);
        return mapper.toResponse(funcionarioRepository.save(funcionario));
    }

    public Funcionario deletarFuncionario (String id) {
        var funcionarioId = UUID.fromString(id);
        Funcionario funcionario = funcionarioRepository
                .findById(funcionarioId)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não existente."));
        return mapper.toResponse(funcionarioRepository.delete(funcionario));
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

    public Funcionario atualizarDadosFuncionario (CriarFuncionarioRequestDTO dto, String  id) {
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
