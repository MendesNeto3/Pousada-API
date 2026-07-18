package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.mapper.QuartoMapper;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import PousadaAPI.dto.response.QuartoResponse;
import PousadaAPI.service.QuartoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;
    private final QuartoMapper quartoMapper;
    private final URIConfig  uriConfig;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarQuarto(
            @RequestBody @Valid CriarQuartoRequestDto quartoRequestDto) {
         Quarto quarto = (Quarto) quartoService.cadastrarQuarto(quartoRequestDto);
         URI location = uriConfig.criarUriLocation(quarto);
         return ResponseEntity.created(location).build();
    }

    @PatchMapping("/atualizar")
    public ResponseEntity<Object> atualizarQuarto (@RequestBody @Valid QuartoResponse dto) {
        var responseAtualizado = quartoService.atualizarStatusQuarto(dto);
        return ResponseEntity.ok(responseAtualizado);
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarQuartosDisponiveis(
            @RequestParam(value = "checkin", required = false) LocalDate checkin,
            @RequestParam(value = "checkout", required = false) LocalDate checkout
    ) {
        List<Quarto> quartoList = quartoService.listarDisponiveis(checkin, checkout);
        List<QuartoResponse> dtoList = quartoList
                .stream()
                .map(quartoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarQuarto (@PathVariable String id) {
        Quarto quarto = quartoService.excluirQuarto(id);
        QuartoResponse responseQuarto = quartoMapper.toResponse(quarto);
        return ResponseEntity.ok(responseQuarto);
    }
}
