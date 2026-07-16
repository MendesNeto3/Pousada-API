package PousadaAPI.Security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtil, userDetailService);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll()

                            .requestMatchers(HttpMethod.POST, "/hospedes").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.GET, "/hospedes/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.PUT, "/hospedes/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.DELETE, "/hospedes/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/quartos/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/quartos/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/quartos/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.DELETE, "/quartos/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/reservas").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.PATCH, "/reservas/**").hasAnyRole("ADMIN", "RECEPCIONISTA")

                            .requestMatchers(HttpMethod.POST, "/pagamentos").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.GET, "/pagamentos/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.DELETE, "/pagamentos").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/funcionarios").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.GET, "/funcionarios/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.PUT, "/funcionarios").hasAnyRole("ADMIN", "RECEPCIONISTA")
                            .requestMatchers(HttpMethod.DELETE, "/funcionarios/**").hasRole("ADMIN")

                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
