package PousadaAPI.controller;

import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.repository.ReservaRepository;
import PousadaAPI.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final ResponseMapper responseMapper;
    private final ReservaRepository reservaRepository;

    @PostMapping
    public ResponseEntity<Pagamento> registrarPagamento (@RequestBody @Valid String ReservaId) {
        Pagamento pagamento = pagamentoService.registrarPagamento(ReservaId);
        Pagamento responseDto = responseMapper.toDTO(pagamento);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<Pagamento> removerPagamento (@RequestBody @Valid String ReservaId) {
        Pagamento pagamento = (Pagamento) pagamentoService.excluirPagamento(ReservaId);
        Pagamento responseDto = responseMapper.toDTO(pagamento);
        return ResponseEntity.ok(responseDto);
    }
}
