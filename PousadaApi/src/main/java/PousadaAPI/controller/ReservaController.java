package PousadaAPI.controller;
import PousadaAPI.config.URIConfig;
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
    private final URIConfig config;

    @PostMapping
    public ResponseEntity<Object> criarReserva(@RequestBody @Valid CriarReservasRequestDto dto) {
        Reserva reservaSalva = (Reserva) service.criarReserva(dto);
        ResponseDto respostaDto = mapper.toDto(reservaSalva);
        URI location = config.criarUriLocation(respostaDto);
        return ResponseEntity.created(location).build();
      }

    @PatchMapping
    public ResponseEntity<Object> realizarCheckin(@RequestBody @Valid Reserva reserva) {
        ResponseDto checkin = (ResponseDto) service.realizarCheckin(reserva);
        return ResponseEntity.ok(checkin);
    }

    @PatchMapping
    public ResponseEntity<Object> realizarCheckout (@RequestBody @Valid Reserva reserva) {
        ResponseDto checkin = (ResponseDto) service.realizarCheckOut(reserva);
        return ResponseEntity.ok(checkin);
    }
}
