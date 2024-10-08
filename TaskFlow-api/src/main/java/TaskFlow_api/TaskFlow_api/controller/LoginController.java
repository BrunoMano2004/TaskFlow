package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.login.LoginDto;
import TaskFlow_api.TaskFlow_api.model.Login;
import TaskFlow_api.TaskFlow_api.security.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.PasswordAuthentication;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTService jwtService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto){

        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJwt = jwtService.gerarToken((Login) authentication.getPrincipal());

        return ResponseEntity.ok(tokenJwt);
    }
}
