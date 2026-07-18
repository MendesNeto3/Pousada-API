package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.mapper.PagamentoMapper;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Controller
@RestController
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;
    private final URIConfig config;

    @PostMapping
    public ResponseEntity<Pagamento> registrarPagamento (@RequestBody @Valid String ReservaId) {
        Pagamento pagamento = pagamentoService.registrarPagamento(ReservaId);
        Pagamento responseDto = pagamentoMapper.toResponse(pagamento);
        URI location = config.criarUriLocation(responseDto);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/calcular")
    public ResponseEntity<Pagamento> calcularSaldoAberto (@RequestParam @Valid String ReservaId) {
        BigDecimal saldoAberto = pagamentoService.CalcularSaldoAberto(ReservaId);
        Pagamento responseDTO = pagamentoMapper.toResponse(saldoAberto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping
    public ResponseEntity<Pagamento> cancelarPagamento (@RequestBody @Valid String ReservaId) {
        Pagamento pagamento = pagamentoService.cancelarPagamentoExpirado(ReservaId);
        Pagamento responseDto = pagamentoMapper.toResponse(pagamento);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/pagamentosLista")
    public ResponseEntity<List<Object>> listaPagamentos (
            @RequestParam @Valid Pagamento pagamento,
            @RequestParam @Valid StatusPagamento saldoStatus) {
        return ResponseEntity.ok(pagamentoService.listaPagamento(pagamento));
    }
}
