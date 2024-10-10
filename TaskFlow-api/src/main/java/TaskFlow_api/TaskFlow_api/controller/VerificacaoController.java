package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.service.CodigoVerificacaoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/codigo")
public class VerificacaoController {

    @Autowired
    private CodigoVerificacaoService codigoVerificacaoService;

    @PostMapping("/validar")
    public ResponseEntity<String> validarCodigo(@RequestHeader(name = "2FA") String codigo){
        codigoVerificacaoService.validarCodigo(codigo);
        return ResponseEntity.ok("Email verificado com sucesso");
    }

    @PostMapping("/reenviar/{username}")
    public ResponseEntity<String> reenviarCodigo(@PathVariable String username) throws Exception {
        codigoVerificacaoService.gerarCodigo(username);
        return ResponseEntity.ok("Código reenviado com sucesso");
    }
}
