package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;

import PousadaAPI.dto.request.CriarHospedeRequestDto;
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
    private final URIConfig config;

    @PostMapping
    public ResponseEntity<PagamentoResponse> registrarPagamento(@RequestBody @Valid CriarPagamentoRequest request) {
        PagamentoResponse pagamento = pagamentoService.registrarPagamento(request);
        URI location = config.criarUriLocation(pagamento);
        return ResponseEntity.created(location).body(pagamento);
    }

    @GetMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularSaldoAberto(@RequestParam("reservaId")UUID reservaId) {
        BigDecimal saldoAberto = pagamentoService.CalcularSaldoAberto(reservaId);
        return ResponseEntity.ok(saldoAberto);
    }

    @DeleteMapping("/{reservaId}")
    public ResponseEntity<PagamentoResponse> cancelarPagamentoExpirado(@PathVariable("reservaId") UUID reservaId) {
        PagamentoResponse response = pagamentoService.cancelarPagamentoExpirado(reservaId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/pagamentosLista")
    public ResponseEntity<List<Object>> listaPagamentos(@ModelAttribute PagamentoResponse filtro) {
        return ResponseEntity.ok(pagamentoService.listaPagamento(filtro));
    }
}
