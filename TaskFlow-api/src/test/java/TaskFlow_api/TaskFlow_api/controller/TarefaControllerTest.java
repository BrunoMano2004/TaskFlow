package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.AtualizacaoTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.DataAlreadyExistException;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.exception.TaskAlreadyMadeException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.service.TarefaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(TarefaController.class)
class TarefaControllerTest {

    @MockBean
    private CadastroEtiquetaDto cadastroEtiqueta;

    @MockBean
    private CadastroUsuarioDto cadastroUsuario;

    @MockBean
    private Etiqueta etiqueta;

    @MockBean
    private Usuario usuario;

    @MockBean
    private CadastroTarefaDto cadastroTarefa;

    @Autowired
    private CadastroTarefaDto cadastroTarefa1;

    @MockBean
    private ListagemTarefaDto listagemTarefa;

    @MockBean
    private AtualizacaoTarefaDto atualizacaoTarefa;

    @MockBean
    private Tarefa tarefa;

    @Autowired
    private Tarefa tarefa1;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TarefaService tarefaService;

    @BeforeEach
    void setUp(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        usuario = new Usuario(cadastroUsuario);

        cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#840695",
                1L
        );

        etiqueta = new Etiqueta(cadastroEtiqueta, usuario);

        cadastroTarefa = new CadastroTarefaDto(
                "Organizar Tabela Excel",
                "Ordenar tabela clientes no excel",
                LocalDateTime.now().plusDays(2L).format(dtf),
                1L,
                1L
        );

        cadastroTarefa1 = new CadastroTarefaDto(
                "Montar apresentação",
                "Montar apresentação falando sobre os reusltados",
                LocalDateTime.now().plusDays(2L).format(dtf),
                1L,
                1L
        );

        atualizacaoTarefa = new AtualizacaoTarefaDto(
                "Organizar tabela",
                "Ordenar",
                1L
        );

        tarefa1 = new Tarefa(cadastroTarefa1, etiqueta, usuario);
        tarefa = new Tarefa(cadastroTarefa, etiqueta, usuario);

        listagemTarefa = new ListagemTarefaDto(tarefa);
    }

    @Test
    void deveriaRetornarTarefaExistentePeloId() throws Exception {

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(listagemTarefa);

        when(tarefaService.buscarTarefaPeloId(anyLong())).thenReturn(listagemTarefa);

        mvc.perform(get("/tarefa/id/{idTarefa}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void deveriaRetornarCodigo404ComTarefaNaoEncontradaPeloId() throws Exception {
        when(tarefaService.buscarTarefaPeloId(anyLong()))
                .thenThrow(new ResourceNotFoundException("Tarefa não encontrada pelo id!"));

        mvc.perform(get("/tarefa/id/{idTarefa}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Tarefa não encontrada pelo id!"));
    }

    @Test
    void deveriaRetornarTodasTarefasDeUmUsuarioComUsuarioEncontrado() throws Exception {

        List<Tarefa> tarefas = Arrays.asList(tarefa, tarefa1);
        List<ListagemTarefaDto> listagemTarefas = tarefas.stream()
                        .map(ListagemTarefaDto::new)
                                .toList();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(listagemTarefas);

        when(tarefaService.buscarTodasTarefasDeUmUsuario(anyLong())).thenReturn(listagemTarefas);

        mvc.perform(get("/tarefa/usuario/{idUsuario}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void deveriaRetornarErro404ComUsuarioNaoEncontradoAoTentarListarTodasAsTarefas() throws Exception {

        when(tarefaService.buscarTodasTarefasDeUmUsuario(anyLong()))
                .thenThrow(new ResourceNotFoundException("Usuário não encontrado!"));

        mvc.perform(get("/tarefa/usuario/{idUsuario}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado!"));
    }

    @Test
    void deveriaRetornarListaDeTarefasQuePossuemAEtiqueta() throws Exception{

        List<Tarefa> tarefas = Arrays.asList(tarefa, tarefa1);
        List<ListagemTarefaDto> listagemTarefas = tarefas.stream()
                        .map(ListagemTarefaDto::new)
                                .toList();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(listagemTarefas);

        when(tarefaService.buscarTodasTarefasPorEtiqueta(anyString(), anyLong()))
                .thenReturn(listagemTarefas);

        mvc.perform(get("/tarefa/etiqueta/{nomeEtiqueta}/{idUsuario}", "Trabalho", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void deveriaRetornarStatus404ComUsuarioNaoEncontradoAoProcurarTarefasPorEtiqueta() throws Exception{
        doThrow(new ResourceNotFoundException("Usuário não encontrado!"))
                .when(tarefaService)
                .buscarTodasTarefasPorEtiqueta(anyString(), anyLong());

        mvc.perform(get("/tarefa/etiqueta/{nomeEtiqueta}/{idUsuario}", "Trabalho", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado!"));
    }

    @Test
    void deveriaRetornarCodigo201DeCreatedParaTarefaCadastradaComSucesso() throws Exception {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = om.writeValueAsString(cadastroTarefa);

        mvc.perform(post("/tarefa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isCreated());
    }

    @Test
    void deveriaRetornarCodigo404ComUsuarioOuEtiquetaNaoEncontradoAoCadastrarTarefa() throws Exception{

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = om.writeValueAsString(cadastroTarefa);

        doThrow(new ResourceNotFoundException("Usuário não encontrado!"))
                .when(tarefaService).criarTarefa(cadastroTarefa);

        mvc.perform(post("/tarefa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado!"));
    }

    @Test
    void deveriaRetornarCodigo400ComAlgumDadoIncorretoAoCadastrarTarefa() throws Exception{

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        cadastroTarefa = new CadastroTarefaDto(
                "Organizar casa",
                "Organizar a casa",
                "10/10/20 24h50",
                1L,
                1L
        );

        TratamentoErroBeanValidation erro =
                new TratamentoErroBeanValidation(
                        "dataExpiracao",
                        "Data e hora inválidas! Formato correto: dd/MM/yyyy HH:mm.");

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonCadastro = om.writeValueAsString(cadastroTarefa);
        String jsonErros = om.writeValueAsString(List.of(erro));

        mvc.perform(post("/tarefa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCadastro)
        ).andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErros));
    }

    @Test
    void deveriaRetornarCodigo409ComNomeDaTarefaRepetido() throws Exception{

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = om.writeValueAsString(cadastroTarefa);

        doThrow(new DataAlreadyExistException("Nome de tarefa já aexistente"))
                .when(tarefaService).criarTarefa(cadastroTarefa);

        mvc.perform(post("/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isConflict())
                .andExpect(content().string("Nome de tarefa já aexistente"));
    }

    @Test
    void deveriaRetornarCodigo204DeNoContentComStatusDaTarefaAtualizadoParaConcluido() throws Exception {

        mvc.perform(patch("/tarefa/{idTarefa}/concluir", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveriaRetornarCodigo404ComTarefaNaoEncontradaAoTentarAtualizarOStatusDaTarefaParaConcluida() throws Exception {

        doThrow(new ResourceNotFoundException("Tarefa não encontrada!"))
                .when(tarefaService).concluirTarefa(1L);

        mvc.perform(patch("/tarefa/{idTarefa}/concluir", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Tarefa não encontrada!"));
    }

    @Test
    void deveriaRetornarCodigo409DeConflictComTarefaJaConcluidaAoTentarConcluirNovamente() throws Exception {

        doThrow(new TaskAlreadyMadeException("Tarefa já concluída!"))
                .when(tarefaService).concluirTarefa(1L);

        mvc.perform(patch("/tarefa/{idTarefa}/concluir", 1))
                .andExpect(status().isConflict())
                .andExpect(content().string("Tarefa já concluída!"));
    }

    public record TratamentoErroBeanValidation(
            String campo,
            String mensagem
    ){
    }
}