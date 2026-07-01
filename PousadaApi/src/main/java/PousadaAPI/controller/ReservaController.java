package PousadaAPI.controller;
import PousadaAPI.domain.exception.ReservaNaoDisponivelException;
import PousadaAPI.domain.mapper.ReservaMapper;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.dto.response.ResponseDto;
import PousadaAPI.dto.response.dtoErroResposta;
import PousadaAPI.service.ReservaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RequestMapping("/reserva")
@RestController
@AllArgsConstructor
public class ReservaController {

    private final ReservaMapper mapper;
    private final ReservaService service;

    @PostMapping
    public ResponseEntity<Object> criarReserva(@RequestBody @Valid CriarReservasRequestDto dto) {
        try {
            Reserva reservaSalva = (Reserva) service.criarReserva(dto);
            ResponseDto respostaDto = mapper.toDto(reservaSalva);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(respostaDto)
                    .toUri();

            return ResponseEntity.created(location).body(respostaDto);
        } catch (ReservaNaoDisponivelException e) {
            var erroDto = dtoErroResposta.respostapadrao(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @PatchMapping
    public ResponseEntity<Object> realizarCheckin(@RequestBody @Valid Reserva reserva) {
        ResponseDto checkin = (ResponseDto) service.realizarCheckin(reserva);
        return ResponseEntity.ok(checkin);
    }

}
