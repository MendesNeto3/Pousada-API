package PousadaAPI.service;
import PousadaAPI.domain.exception.DadosInvalidosException;
import PousadaAPI.domain.exception.HospedeNaoEncontradoException;
import PousadaAPI.domain.exception.RegistroDuplicadoException;
import PousadaAPI.domain.mapper.HospedeMapper;
import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.response.ResponseDto;
import PousadaAPI.dto.response.dtoHospedePesquisa;
import PousadaAPI.repository.HospedeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
@Slf4j
public class HospedeService {

    private final HospedeRepository repository;
    private final HospedeMapper mapper;
    private final ResponseMapper responseMapper;
    private final PasswordEncoder passwordEncoder;

    public Object salvarHospede(Hospede hospede) {
        if (repository.existsByCpf(hospede.getCpf())) {
            log.error("Erro ao cadastrar o usuário.");
            throw new RegistroDuplicadoException("Este CPF já foi cadastrado.");
        } else if (repository.existsByEmail(hospede.getEmail())) {
            throw new RegistroDuplicadoException("Esté email já foi registrado.");
        }
        return repository.save(hospede);
    }

    public ResponseDto buscarPorID(UUID id) {
        Hospede hospede = repository.findById(id)
                .orElseThrow(() ->
                        new HospedeNaoEncontradoException("O Hospede não foi encontrado."));
        return responseMapper.toResponse(hospede);
    }

    public Object deletarHospede(UUID id) {
       Hospede hospede = (Hospede) repository.findById(id)
               .orElseThrow(() ->
                       new HospedeNaoEncontradoException("O Hospede não foi encontrado."));
        repository.delete(hospede);
        return responseMapper.toResponse(hospede);
    }

    public List<Hospede> pesquisa(dtoHospedePesquisa filtros) {
        var hospede = new Hospede();

        hospede.setNome(filtros.nome());
        hospede.setTelefone(filtros.telefone());
        hospede.setCpf(filtros.cpf());
        hospede.setEmail(filtros.email());

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Hospede> example = Example.of(hospede, matcher);

        return repository.findAll(example);
    }

    public Object buscarPorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new HospedeNaoEncontradoException("O hóspede não foi encontrado.");
        } else {
            Hospede hospede = repository.findByNome(nome);
             return mapper.toDTO(hospede);
        }
    }

    public ResponseDto atualizar(HospedeDTO dto, String id) {
        UUID idHospede = UUID.fromString(id);
        Hospede hospede = repository
                .findById(idHospede)
                .orElseThrow(() ->
                        new HospedeNaoEncontradoException(
                                "O hóspede não foi encontrado."
                        ));
        if (dto.nome() == null || dto.nome().isEmpty() ||
            dto.telefone() == null || dto.telefone().isEmpty() ||
            dto.cpf() == null || dto.cpf().isEmpty() ||
            dto.email() == null || dto.email().isEmpty()) {

            throw new DadosInvalidosException(
                    "Os dados inseridos são inválidos."
            );
        }
        hospede.setNome(dto.nome());
        hospede.setTelefone(dto.telefone());
        hospede.setCpf(dto.cpf());
        hospede.setEmail(dto.email());

        Hospede hospedeAtualizado = repository.save(hospede);

        return responseMapper.toResponse(hospedeAtualizado);
    }

    public ResponseDto obterDetalhes(UUID id) {
        Optional<Hospede> hospede = repository.findById(id);

        if (hospede.isEmpty()) {
            new HospedeNaoEncontradoException("Hospede não foi encontrado.");
        }
        return responseMapper.toResponse(hospede.get());
    }
}
