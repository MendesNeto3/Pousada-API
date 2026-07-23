package PousadaAPI.controller;
import PousadaAPI.config.URIConfig;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.dto.response.ReservasResumoDTO;
import PousadaAPI.service.ReservaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.UUID;

@RequestMapping("/reserva")
@RestController
@AllArgsConstructor
public class ReservaController {
    private final ReservaService service;
    private final URIConfig config;

    @PostMapping
    public ResponseEntity<ReservasResumoDTO> criarReserva(@RequestBody @Valid CriarReservasRequestDto dto) {
        ReservasResumoDTO reservaSalva = service.criarReserva(dto);
        URI location = config.criarUriLocation(reservaSalva);
        return ResponseEntity.created(location).build();
      }

    @PatchMapping("/{id}/checkin")
    public ResponseEntity<ReservasResumoDTO> realizarCheckin(@PathVariable("id") UUID id) {
        ReservasResumoDTO checkin =  service.realizarCheckin(id);
        return ResponseEntity.ok(checkin);
    }

    @PatchMapping("/{id}/checkout")
    public ResponseEntity<ReservasResumoDTO> realizarCheckout (@PathVariable("id") UUID id) {
        ReservasResumoDTO checkin = service.realizarCheckOut(id);
        return ResponseEntity.ok(checkin);
    }

    @PatchMapping("/{id}/cancelamento")
    public ResponseEntity<ReservasResumoDTO> realizarCancelamento(@PathVariable("id") @Valid String id) {
        ReservasResumoDTO reservaCancelada = service.cancelarReserva(id);
        return ResponseEntity.ok(reservaCancelada);
    }
}
