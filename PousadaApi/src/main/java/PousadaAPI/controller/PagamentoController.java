package PousadaAPI.controller;

import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.dtoEntity.PagamentoDTO;
import PousadaAPI.dto.response.ResponseDto;
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

    @PostMapping
    public ResponseEntity<Pagamento> registrarPagamento (@RequestBody @Valid String id) {
        Pagamento pagamento = pagamentoService.registrarPagamento(id);
        Pagamento responseDto = responseMapper.toDTO(pagamento);
        return ResponseEntity.ok(responseDto);
    }
}
