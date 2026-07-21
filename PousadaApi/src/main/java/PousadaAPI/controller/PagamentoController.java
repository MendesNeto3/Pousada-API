package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
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
        URI location = config.criarUriLocation(pagamento);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularSaldoAberto (@RequestParam @Valid String ReservaId) {
        BigDecimal saldoAberto = pagamentoService.CalcularSaldoAberto(ReservaId);
        return ResponseEntity.ok(saldoAberto);
    }

    @DeleteMapping
    public ResponseEntity<Pagamento> cancelarPagamento (@RequestBody @Valid String ReservaId) {
        Pagamento pagamento = pagamentoService.cancelarPagamentoExpirado(ReservaId);
        return ResponseEntity.ok(pagamento);
    }

    @GetMapping("/pagamentosLista")
    public ResponseEntity<List<Object>> listaPagamentos (
            @RequestParam @Valid Pagamento pagamento){
        return ResponseEntity.ok(pagamentoService.listaPagamento(pagamento));
    }
}
