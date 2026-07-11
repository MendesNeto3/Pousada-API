package PousadaAPI.Security;

import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new
                        UsernameNotFoundException("Funcionario não encontrado:" + email));
        return User.withUsername(funcionario.getEmail())
                .password(funcionario.getSenha())
                .roles("USER")
                .build();
    }


}
