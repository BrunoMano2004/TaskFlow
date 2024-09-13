package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{email}")
    public ResponseEntity<ListagemUsuarioDto> retornaUsuarioPorEmail(@PathVariable String email){
        ListagemUsuarioDto usuario = usuarioService.retornarUsuario(email);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDto cadastroUsuario){
        usuarioService.cadastrarUsuario(cadastroUsuario);
        return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.CREATED);
    }
}
