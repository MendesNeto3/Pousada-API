package PousadaAPI.controller;

import PousadaAPI.dto.dtoEntity.LoginDTO;
import PousadaAPI.dto.response.TokenResponseDTO;
import PousadaAPI.service.AutenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/autentication")
@AllArgsConstructor
public class AutenticationController {

    private final AutenticationService autenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        TokenResponseDTO response = autenticationService.autenticar(dto);
        return ResponseEntity.ok(response);
    }
}