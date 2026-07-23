package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.request.CriarHospedeRequestDto;
import PousadaAPI.dto.response.HospedeResponse;
import PousadaAPI.dto.response.dtoHospedePesquisa;
import PousadaAPI.service.HospedeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hospede")
@AllArgsConstructor
public class HospedeController {

    private final HospedeService service;
    private final URIConfig config;

    @PostMapping
    public ResponseEntity<Void> cadastrarHospede(@RequestBody @Valid CriarHospedeRequestDto dto) {
        HospedeResponse salvo = service.salvarHospede(dto);
        URI location = config.criarUriLocation(salvo);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<HospedeResponse> procurarPorNome(@PathVariable("nome") String nome) {
        HospedeResponse hospede = service.buscarPorNome(nome);
        return ResponseEntity.ok(hospede);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospedeResponse> procurarPorID(@PathVariable("id") UUID id) {
        HospedeResponse hospede = service.buscarPorID(id);
        return ResponseEntity.ok(hospede);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarHospede(@PathVariable("id") UUID id) {
        Object hospedeDeletado = service.deletarHospede(id);
        return ResponseEntity.ok(hospedeDeletado);
    }

    @GetMapping
    public ResponseEntity<List<Hospede>> pesquisa(@ModelAttribute dtoHospedePesquisa filtros) {
        return ResponseEntity.ok(service.pesquisa(filtros));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospedeResponse> atualizar(
            @PathVariable("id") String id,
            @RequestBody @Valid HospedeResponse dto) {
        HospedeResponse responseAtualizado = service.atualizar(dto, id);
        return ResponseEntity.ok(responseAtualizado);
    }

    @GetMapping("/busca-rapida/{id}")
    public ResponseEntity<HospedeResponse> obterDetalhesHospede(@PathVariable("id") UUID id) {
        HospedeResponse dto = service.obterDetalhes(id);
        return ResponseEntity.ok(dto);
    }
}