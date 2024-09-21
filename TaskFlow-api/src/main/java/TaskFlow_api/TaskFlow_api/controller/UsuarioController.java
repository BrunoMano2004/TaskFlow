package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.usuario.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Retorna um usuário", description = "Este endpoint retorna um usuário, pelo id dele, utilizando uma DTO", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Usuário retornado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "Usuário não encontrado com o id fornecido",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/id/{idUsuario}")
    public ResponseEntity<ListagemUsuarioDto> retornarUsuarioPeliId(@PathVariable Long idUsuario){
        ListagemUsuarioDto listagemUsuario = usuarioService.retornarUsuarioPeloId(idUsuario);
        return ResponseEntity.ok(listagemUsuario);
    }


    @Operation(summary = "Retorna um usuário", description = "Este endpoint retorna um usuário, pelo endereço de email dele, utilizando uma DTO", responses = {
            @ApiResponse(responseCode = "200",
                        description = "Usuário retornado com sucesso",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                        description = "Usuário não encontrado com o email fornecido",
                        content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<ListagemUsuarioDto> retornaUsuarioPorEmail(@PathVariable String email){
        ListagemUsuarioDto usuario = usuarioService.retornarUsuarioPeloEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Cadastra um usuário", description = "Este endpoint cadastra um usuário utilizando uma DTO", responses = {
            @ApiResponse(responseCode = "201",
                        description = "Usuário criado com sucesso",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                        description = "Algum dado digitado de forma inválida",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409",
                        description = "Email conflitante com outro já existente na base de dados",
                        content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDto cadastroUsuario){
        usuarioService.cadastrarUsuario(cadastroUsuario);
        return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um usuário", description = "Este endpoint atualiza em partes um usuário, através de uma DTO", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                    description = "Algum dado digitado de forma inválida",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409",
                    description = "Email conflitante com outro já existente na base de dados",
                    content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{email}")
    @Transactional
    public ResponseEntity<ListagemUsuarioDto> atualizarUsuario(@PathVariable String email,
                                                               @RequestBody @Valid AtualizacaoUsuarioDto atualizacaoUsuario){
        ListagemUsuarioDto listagemUsuario = usuarioService.atualizarUsuario(email, atualizacaoUsuario);

        return new ResponseEntity<>(listagemUsuario, HttpStatus.OK);
    }

    @Operation(summary = "Exclui o usuário", description = "Deleta o usuário do banco de dados, junto com suas tarefas e etiquetas",
            responses = {
            @ApiResponse(responseCode = "204",
                    description = "Usuário deletada com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "Usuário não encontrada com o id passado",
                    content = @Content(mediaType = "applciation/json"))
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> excluirUsuario(@PathVariable Long idUsuario){
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
