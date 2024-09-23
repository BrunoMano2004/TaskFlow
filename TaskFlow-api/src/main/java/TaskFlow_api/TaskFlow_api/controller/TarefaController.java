package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/{idTarefa}")
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
                            description = "Usuario não encoontrado com o id especificado",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ListagemTarefaDto>> retornarTodasTarefasDeUmUsuario(@PathVariable Long idUsuario){
        List<ListagemTarefaDto> listagemTarefa = tarefaService.buscarTodasTarefasDeUmUsuario(idUsuario);

        return ResponseEntity.ok(listagemTarefa);
    }


}
