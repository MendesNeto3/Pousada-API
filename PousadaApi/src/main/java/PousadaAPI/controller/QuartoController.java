package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import PousadaAPI.dto.response.QuartoResponse;
import PousadaAPI.service.QuartoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;
    private final URIConfig  uriConfig;

    @PostMapping("/cadastrar")
    public ResponseEntity<QuartoResponse> cadastrarQuarto(
            @RequestBody @Valid CriarQuartoRequestDto quartoRequestDto) {
         QuartoResponse quarto =  quartoService.cadastrarQuarto(quartoRequestDto);
         URI location = uriConfig.criarUriLocation(quarto);
         return ResponseEntity.created(location).build();
    }

    @PatchMapping("/atualizar")
    public ResponseEntity<Object> atualizarQuarto (@RequestBody @Valid CriarQuartoRequestDto dto) {
        var responseAtualizado = quartoService.atualizarStatusQuarto(dto);
        return ResponseEntity.ok(responseAtualizado);
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarQuartosDisponiveis(
            @RequestParam(value = "checkin", required = false) LocalDate checkin,
            @RequestParam(value = "checkout", required = false) LocalDate checkout
    ) {
       return ResponseEntity.ok(quartoService.listarDisponiveis(checkin, checkout));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<QuartoResponse> deletarQuarto (@PathVariable String id) {
        QuartoResponse quarto = quartoService.excluirQuarto(id);
        return ResponseEntity.ok(quarto);
    }
}
