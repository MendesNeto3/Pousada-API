package PousadaAPI.controller;
import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.mapper.ReservaMapper;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.dto.response.ReservasResumoDTO;
import PousadaAPI.service.ReservaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        URI location = config.criarUriLocation(reservaSalva);
        return ResponseEntity.created(location).build();
      }

    @PatchMapping("/checkin")
    public ResponseEntity<Object> realizarCheckin(@RequestBody @Valid Reserva reserva) {
        ReservasResumoDTO checkin = (ReservasResumoDTO) service.realizarCheckin(reserva);
        return ResponseEntity.ok(checkin);
    }

    @PatchMapping("/checkout")
    public ResponseEntity<Object> realizarCheckout (@RequestBody @Valid Reserva reserva) {
        ReservasResumoDTO checkin = (ReservasResumoDTO) service.realizarCheckOut(reserva);
        return ResponseEntity.ok(checkin);
    }

    @PatchMapping("/cancelamento")
    public ResponseEntity<Object> realizarCancelamento(@RequestBody @Valid String id) {
        Reserva reservaCancelada = service.cancelarReserva(id);
        return ResponseEntity.ok(reservaCancelada);
    }
}
