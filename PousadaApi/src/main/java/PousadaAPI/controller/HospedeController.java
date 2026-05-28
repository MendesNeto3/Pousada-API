package PousadaAPI.controller;
import PousadaAPI.domain.exception.CadastroIndisponivelException;
import PousadaAPI.domain.exception.HospedeNaoEncontradoException;
import PousadaAPI.domain.exception.RegistroDuplicadoException;
import PousadaAPI.domain.mapper.HospedeMapper;
import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.request.CriarHospedeRequestDto;
import PousadaAPI.dto.response.ResponseDto;
import PousadaAPI.dto.response.dtoErroResposta;
import PousadaAPI.dto.response.dtoHospedePesquisa;
import PousadaAPI.service.HospedeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/hospede")
@RestController
@AllArgsConstructor
public class HospedeController {

    private final HospedeService service;
    private final HospedeMapper mapper;
    private final ResponseMapper responseMapper;

    @PostMapping
    public ResponseEntity<Object> cadastrarHospede(@RequestBody @Valid CriarHospedeRequestDto dto) {
        try {
            Hospede hospede = mapper.toEntity(dto);
            Hospede salvo = (Hospede) service.salvarHospede(hospede);
            ResponseDto response = responseMapper.toResponse(salvo);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.hospede().id())
                    .toUri();

            return ResponseEntity.created(location).body(response);
        } catch (CadastroIndisponivelException e) {
            var erro = dtoErroResposta.respostapadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        } catch (RegistroDuplicadoException e) {
            var error = dtoErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Object> procurarPorNome(@PathVariable String nome) {
        try {
            Hospede hospede = (Hospede) service.buscarPorNome(nome);
            return ResponseEntity.ok(mapper.toDTO(hospede));
        } catch (HospedeNaoEncontradoException e) {
            var erro = dtoErroResposta.respostapadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> procurarPorID(@PathVariable("id") String id) {
        try {
            var idHospede = UUID.fromString(id);
            ResponseDto hospede = service.buscarPorID(idHospede);

            return ResponseEntity.ok(hospede);
        }catch (HospedeNaoEncontradoException e){
            var erro = dtoErroResposta.respostapadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarHospede(@PathVariable("id") String id) {
        try {
            var idHospede = UUID.fromString(id);
            Object hospedeDeletado = service.deletarHospede(idHospede);

            return ResponseEntity.ok(hospedeDeletado);

        } catch (HospedeNaoEncontradoException e){
            var erro = dtoErroResposta.respostapadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Formado de UUID inválido");
        }
    }

    @GetMapping
    public ResponseEntity<List<Hospede>> pesquisa(dtoHospedePesquisa filtros) {
        return ResponseEntity.ok(service.pesquisa(filtros));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseDto> atualizar (
            @PathVariable("id") String id, @RequestBody @Valid HospedeDTO dto) {
        var responseAtualizado = service.atualizar(dto, id);
        return ResponseEntity.ok(responseAtualizado);
    }

    @GetMapping("/busca-rapida/{id}")
    public ResponseEntity<ResponseDto> obterDetalhesHospede(@PathVariable("id") UUID id) {
        ResponseDto dto = service.obterDetalhes(id);
        return ResponseEntity.ok(dto);
    }
}

