package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.service.EtiquetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etiqueta")
public class EtiquetaController {

    @Autowired
    private EtiquetaService etiquetaService;

    @Operation(summary = "Retorna etiqueta pelo id", description = "Retornar etiqueta pesquisando pelo id", responses = {
            @ApiResponse(responseCode = "200",
                        description = "Etiqueta encontrada com sucesso!",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                        description = "Etiqueta não encontrada!",
                        content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/id/{idEtiqueta}")
    public ResponseEntity<ListagemEtiquetaDto> retornarEtiquetaPeloId(@PathVariable Long idEtiqueta){
        ListagemEtiquetaDto listagemEtiqueta = etiquetaService.retornarEtiquetaPeloId(idEtiqueta);
        return ResponseEntity.ok(listagemEtiqueta);
    }

    @Operation(summary = "Retorna etiqueta", description = "Retorna etiqueta única de um usuário pelo nome da etiqueta e pelo id do usuário", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna etiqueta com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "Usuario não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{nomeEtiqueta}/{idUsuario}")
    public ResponseEntity<List<ListagemEtiquetaDto>> buscarEtiquetaPeloNome(@PathVariable String nomeEtiqueta,
                                                                     @PathVariable Long idUsuario){
        List<ListagemEtiquetaDto> listagemEtiqueta = etiquetaService.buscarEtiqueta(nomeEtiqueta, idUsuario);
        return ResponseEntity.ok(listagemEtiqueta);
    }

    @Operation(summary = "Retorna lista de etiquetas", description = "Retorna a lista de etiquetas que um usuário possui", responses = {
            @ApiResponse(responseCode = "200",
                        description = "Retorna lista de etiquetas, mesmo estando vazia",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                        description = "Usuário não encontrado!",
                        content = @Content(mediaType = "Application/json"))
    })
    @GetMapping("/usuario/{emailUsuario}")
    public ResponseEntity<List<ListagemEtiquetaDto>> listarTodasEtiquetasPorUsuario(@PathVariable String emailUsuario){
        List<ListagemEtiquetaDto> listaEtiquetas = etiquetaService.retornarTodasEtiquetasPorUsuario(emailUsuario);
        return ResponseEntity.ok(listaEtiquetas);
    }

    @Operation(summary = "Cadastra uma etiqueta para um cliente", description = "Cadastra uma etiqueta para um usuário escolhido passando por algumas validações", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Etiqueta criado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                        description = "Etiqueta não encontrado pelo id passado",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                        description = "Dados digitados incorretamente ou em branco",
                        content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<String> cadastrarNovaEtiqueta(@RequestBody @Valid CadastroEtiquetaDto cadastroEtiqueta){
        etiquetaService.cadastrarEtiqueta(cadastroEtiqueta);
        return new ResponseEntity<>("Etiqueta criada com sucesso!", HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui a etiqueta", description = "Deleta a etiqueta do banco de dados", responses = {
            @ApiResponse(responseCode = "204",
                        description = "Etiqueta deletada com sucesso",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                        description = "Etiqueta não encontrada com o id passado",
                        content = @Content(mediaType = "applciation/json"))
    })
    @DeleteMapping("/{idEtiqueta}")
    @Transactional
    public ResponseEntity<String> deletarEtiqueta(@PathVariable Long idEtiqueta){
        etiquetaService.excluirEtiqueta(idEtiqueta);
        return ResponseEntity.noContent().build();
    }
}
