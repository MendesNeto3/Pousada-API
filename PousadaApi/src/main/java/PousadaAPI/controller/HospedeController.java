package PousadaAPI.controller;
import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.mapper.HospedeMapper;
import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.request.CriarHospedeRequestDto;
import PousadaAPI.dto.response.ResponseDto;
import PousadaAPI.dto.response.dtoHospedePesquisa;
import PousadaAPI.service.HospedeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private final URIConfig config;

    @PostMapping
    public ResponseEntity<Object> cadastrarHospede(@RequestBody @Valid CriarHospedeRequestDto dto) {
            Hospede hospede = mapper.toEntity(dto);
            Hospede salvo = (Hospede) service.salvarHospede(hospede);
            ResponseDto response = responseMapper.toResponse(salvo);
            URI location = config.criarUriLocation(response.hospede().id());
            return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Object> procurarPorNome(@PathVariable String nome) {
            Hospede hospede = (Hospede) service.buscarPorNome(nome);
            return ResponseEntity.ok(mapper.toDTO(hospede));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> procurarPorID(@PathVariable("id") String id) {
            var idHospede = UUID.fromString(id);
            ResponseDto hospede = service.buscarPorID(idHospede);
            return ResponseEntity.ok(hospede);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarHospede(@PathVariable("id") String id) {
            var idHospede = UUID.fromString(id);
            Object hospedeDeletado = service.deletarHospede(idHospede);
            return ResponseEntity.ok(hospedeDeletado);
    }

    @GetMapping
    public ResponseEntity<List<Hospede>> pesquisa(@RequestParam(value = "filtros", required = false) dtoHospedePesquisa filtros) {
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

