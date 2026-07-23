package PousadaAPI.service;
import PousadaAPI.domain.exception.HospedeNaoEncontradoException;
import PousadaAPI.domain.exception.RegistroDuplicadoException;
import PousadaAPI.domain.mapper.HospedeMapper;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.request.CriarHospedeRequestDto;
import PousadaAPI.dto.response.HospedeResponse;
import PousadaAPI.dto.response.dtoHospedePesquisa;
import PousadaAPI.repository.HospedeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospedeService {

    private final HospedeRepository repository;
    private final HospedeMapper mapper;

    public HospedeResponse salvarHospede(CriarHospedeRequestDto dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new RegistroDuplicadoException("Este CPF já foi cadastrado.");
        } else if (repository.existsByEmail(dto.email())) {
            throw new RegistroDuplicadoException("Esté email já foi registrado.");
        }
        Hospede hospede = Hospede.builder()
                .nome(dto.nome())
                .email((dto.email()))
                .cpf(dto.cpf())
                .telefone(dto.telefone())
                .build();

       return mapper.toResponse(repository.save(hospede));
    }

    public HospedeResponse buscarPorID(UUID id) {
        Hospede hospede = repository.findById(id)
                .orElseThrow(() ->
                        new HospedeNaoEncontradoException("O Hospede não foi encontrado."));
        return mapper.toResponse(hospede);
    }

    public HospedeResponse deletarHospede(UUID id) {
       Hospede hospede = repository.findById(id)
               .orElseThrow(() ->
                       new HospedeNaoEncontradoException("O Hospede não foi encontrado."));
        repository.delete(hospede);
        return mapper.toResponse(hospede);
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

    public HospedeResponse buscarPorNome(String nome) {
        return repository.findByNome(nome)
                .filter(hospede -> hospede.getNome() != null && !hospede.getNome().isBlank())
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new HospedeNaoEncontradoException("O hóspede não possui um nome válido ou não foi encontrado."));
    }

    public HospedeResponse atualizar(@Valid HospedeResponse dto, String id) {
        dto.validar();
        return repository
                .findById(UUID.fromString(id))
                .map(hospede -> {
                    hospede.setNome(dto.nome());
                    hospede.setTelefone(dto.telefone());
                    hospede.setCpf(dto.cpf());
                    hospede.setEmail(dto.email());
                    return repository.save(hospede);
                })
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new HospedeNaoEncontradoException("O hóspede não foi encontrado."));
    }

    public HospedeResponse obterDetalhes(UUID id) {
        Optional<Hospede> hospede = repository.findById(id);
        if (hospede.isEmpty()) {
            throw new HospedeNaoEncontradoException("Hospede não foi encontrado.");
        }
        return mapper.toResponse(hospede.get());
    }
}
