package Laboratorio1BdaGrupo5.BackendLab1.authentication.controller;

import Laboratorio1BdaGrupo5.BackendLab1.authentication.entities.AuthenticationResponse;
import Laboratorio1BdaGrupo5.BackendLab1.authentication.entities.LoginRequest;
import Laboratorio1BdaGrupo5.BackendLab1.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}