package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
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
    private final URIConfig  uriConfig;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarQuarto(
            @RequestBody @Valid CriarQuartoRequestDto quartoRequestDto) {
         Quarto quarto = (Quarto) quartoService.cadastrarQuarto(quartoRequestDto);
         ResponseDto responseQuarto = mapper.toResponse(quarto);
         URI location = uriConfig.criarUriLocation(responseQuarto);
         return ResponseEntity.created(location).build();
    }

    @PatchMapping("/atualizar")
    public ResponseEntity<Object> atualizarQuarto (@RequestBody @Valid QuartoDTO dto) {
        var responseAtualizado = quartoService.atualizarStatusQuarto(dto);
        return ResponseEntity.ok(responseAtualizado);
    }

    @GetMapping("/listar")
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
