package PousadaAPI.service;

import PousadaAPI.Security.JwtUtil;
import PousadaAPI.domain.model.Usuario;
import PousadaAPI.dto.response.TokenResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AutenticationService {
    private AuthenticationManager authentication;
    private JwtUtil  jwtUtil;

    public TokenResponseDTO autenticar (String username, String password) {
        var senhaUsuario = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authentication.authenticate(senhaUsuario);
        Usuario usuarioAutenticado =  (Usuario) auth.getPrincipal();
        String token = jwtUtil.generateToken(usuarioAutenticado);

        return new TokenResponseDTO(token);
    }
}
