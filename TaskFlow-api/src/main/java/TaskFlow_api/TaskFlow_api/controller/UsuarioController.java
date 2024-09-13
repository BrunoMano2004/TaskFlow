package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<ListagemUsuarioDto> retornaUsuarioPorId(@PathVariable Long id){
        ListagemUsuarioDto usuario = usuarioService.retornarUsuario(id);
        return ResponseEntity.ok(usuario);
    }


}
