package PousadaAPI.controller;

import PousadaAPI.domain.exception.CadastroIndisponivelException;
import PousadaAPI.domain.mapper.QuartoMapper;
import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.dtoEntity.QuartoDTO;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import PousadaAPI.dto.response.ResponseDto;
import PousadaAPI.dto.response.dtoErroResposta;
import PousadaAPI.service.QuartoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;
    private final QuartoMapper quartoMapper;
    private final ResponseMapper mapper;

    @PostMapping
    public ResponseEntity<?> cadastrarQuarto(
            @RequestBody @Valid CriarQuartoRequestDto quartoRequestDto) {
        try {
            Quarto quarto = (Quarto) quartoService.cadastrarQuarto(quartoRequestDto);
            ResponseDto responseQuarto = mapper.toResponse(quarto);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(quarto.getId())
                    .toUri();

            return ResponseEntity.created(location).body(responseQuarto);
        } catch (CadastroIndisponivelException e) {
            var erro = dtoErroResposta.respostapadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @PatchMapping
    public ResponseEntity<Object> atualizarQuarto (@RequestBody @Valid QuartoDTO dto) {
        var responseAtualizado = quartoService.atualizarStatusQuarto(dto);
        return ResponseEntity.ok(responseAtualizado);
    }

    @GetMapping
    public ResponseEntity<Object> listarQuartosDisponiveis(
            @RequestParam(value = "checkin", required = false) LocalDate checkin,
            @RequestParam(value = "checkout", required = false) LocalDate checkout
    ) {
        List<Quarto> quartoList = quartoService.listarDisponiveis(checkin, checkout);
        List<QuartoDTO> dtoList = quartoList
                .stream()
                .map(quartoMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping
    public ResponseEntity<Object> deletarQuarto (@PathVariable String id) {
        var idQuarto = UUID.fromString(id);
        Quarto quarto = quartoService.excluirQuarto(idQuarto.toString());
        ResponseDto responseQuarto = mapper.toResponse(quarto);

        return ResponseEntity.ok(responseQuarto);
    }
}
