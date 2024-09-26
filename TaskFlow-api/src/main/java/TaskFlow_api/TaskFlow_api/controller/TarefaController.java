package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.service.TarefaService;
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
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Operation(summary = "Retorna uma tarefa pelo id",
            description = "Busca e retorna uma tarefa pelo id, através da uma dto",
            responses = {
                @ApiResponse(responseCode = "200",
                            description = "Retorna tarefa encontrada com sucesso",
                            content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "404",
                            description = "Tarefa não encontrada com o id especificado",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/id/{idTarefa}")
    public ResponseEntity<ListagemTarefaDto> retornarTarefaPeloId(@PathVariable Long idTarefa){
        ListagemTarefaDto listagemTarefa = tarefaService.buscarTarefaPeloId(idTarefa);

        return ResponseEntity.ok(listagemTarefa);
    }

    @Operation(summary = "Retorna todas as tarefas de um usuário",
            description = "Busca e retorna todas as tarefas de um usuário através do id do mesmo",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Retorna lista de tarefas encontradas com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404",
                            description = "Usuario não encontrado com o id especificado",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ListagemTarefaDto>> retornarTodasTarefasDeUmUsuario(@PathVariable Long idUsuario){
        List<ListagemTarefaDto> listagemTarefa = tarefaService.buscarTodasTarefasDeUmUsuario(idUsuario);

        return ResponseEntity.ok(listagemTarefa);
    }

    @Operation(summary = "Retorna todas as tarefas com base nas etiquetas",
            description = "Busca e retorna todas as tarefas com base nas etiquetas encontradas pela pesquisa",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Retorna lista de tarefas encontradas com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404",
                            description = "Usuario não encontrado com o id especificado",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/etiqueta/{nomeEtiqueta}/{idUsuario}")
    public ResponseEntity<List<ListagemTarefaDto>> buscarTarefasPorEtiquetaDeUmUsuario(@PathVariable String nomeEtiqueta,
                                                                                       @PathVariable Long idUsuario){
        List<ListagemTarefaDto> tarefas = tarefaService.buscarTodasTarefasPorEtiqueta(nomeEtiqueta, idUsuario);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Cadastra uma tarefa", description = "Cadastra uma tarefa através de uma dto", responses = {
            @ApiResponse(responseCode = "201",
                        description = "Tarefa criada com sucesso",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                        description = "Usuário ou etiqueta não encontrado",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                        description = "Algum dado digitado de forma inválida",
                        content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastroDeTarefa(@RequestBody @Valid CadastroTarefaDto cadastroTarefa){
        tarefaService.criarTarefa(cadastroTarefa);
        return new ResponseEntity<>("Tarefa criada com sucesso", HttpStatus.CREATED);
    }

    @Operation(summary = "Concluir tarefa", description = "Mudar status da tarefa para concluída", responses = {
            @ApiResponse(responseCode = "204",
                    description = "Tarefa concluída com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "Tarefa não encontrada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409",
                    description = "Tarefa já estava concluída ou expirada",
                    content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{idTarefa}/concluir")
    @Transactional
    public ResponseEntity<String> concluirTarefa(@PathVariable Long idTarefa){
        tarefaService.concluirTarefa(idTarefa);
        return ResponseEntity.noContent().build();
    }
}
