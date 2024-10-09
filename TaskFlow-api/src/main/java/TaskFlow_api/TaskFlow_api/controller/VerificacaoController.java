package TaskFlow_api.TaskFlow_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verificar")
public class VerificacaoController {

    @PostMapping
    public ResponseEntity<String> validarCodigo(@RequestHeader(name = "2FA") String codigo){
        return ResponseEntity.ok("Email verificado com sucesso");
    }
}
