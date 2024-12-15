package Laboratorio1BdaGrupo5.BackendLab1.authentication.service;

import Laboratorio1BdaGrupo5.BackendLab1.authentication.entities.AuthenticationResponse;
import Laboratorio1BdaGrupo5.BackendLab1.authentication.entities.LoginRequest;
import Laboratorio1BdaGrupo5.BackendLab1.authentication.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userService.getUserByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user.generateExtraClaims(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .Name(user.getName())
                .build();
    }
}