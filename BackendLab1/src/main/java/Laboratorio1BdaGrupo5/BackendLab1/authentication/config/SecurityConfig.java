package Laboratorio1BdaGrupo5.BackendLab1.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(customizeRequests -> {
                    customizeRequests
                            .requestMatchers("/authenticate/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/producto/**", "/api/categoria/**", "/api/cliente/**", "/api/detalleorden/**", "/api/orden/**", "/api/report/**", "/api/direccion/**", "/api/almacen/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/producto/**", "/api/categoria/**", "/api/cliente/**", "/api/detalleorden/**", "/api/orden/**", "/api/direccion/**", "/api/almacen/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/producto/**", "/api/categoria/**", "/api/cliente/**", "/api/detalleorden/**", "/api/orden", "/api/direccion/**", "/api/almacen/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/producto/**", "/api/categoria/**", "/api/cliente/**", "/api/detalleorden/**", "/api/orden", "/api/direccion/**", "/api/almacen/**").permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
