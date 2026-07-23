package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.mapper.PagamentoMapper;
import PousadaAPI.dto.request.CriarPagamentoRequest;
import PousadaAPI.dto.response.PagamentoResponse;
import PousadaAPI.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RestController
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;
    private final URIConfig config;


    @PostMapping
    public ResponseEntity<PagamentoResponse> registrarPagamento(@RequestBody @Valid CriarPagamentoRequest request) {
        PagamentoResponse pagamento = pagamentoService.registrarPagamento(request);
        URI location = config.criarUriLocation(pagamento);
        return ResponseEntity.created(location).body(pagamento);
    }

    @GetMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularSaldoAberto (@RequestParam @Valid UUID ReservaId) {
        BigDecimal saldoAberto = pagamentoService.CalcularSaldoAberto(ReservaId);
        return ResponseEntity.ok(saldoAberto);
    }

    @DeleteMapping("/{reservaId}")
    public ResponseEntity<PagamentoResponse> cancelarPagamentoExpirado(@PathVariable UUID reservaId) {
        PagamentoResponse response = pagamentoService.cancelarPagamentoExpirado(reservaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pagamentosLista")
    public ResponseEntity<List<Object>> listaPagamentos (
            @RequestParam @Valid PagamentoResponse pagamento){
        return ResponseEntity.ok(pagamentoService.listaPagamento(pagamento));
    }
}
