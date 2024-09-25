package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.AtualizacaoTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.exception.TaskAlreadyMadeException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Status;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.EtiquetaRepository;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.tarefa.ValidacoesTarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Captor
    private ArgumentCaptor<Tarefa> tarefaCaptor;

    @Spy
    private List<ValidacoesTarefa> validacoesTarefa = new ArrayList<>();

    @Mock
    private EtiquetaRepository etiquetaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Autowired
    private CadastroUsuarioDto cadastroUsuario;

    @Autowired
    private CadastroEtiquetaDto cadastroEtiqueta;

    @Autowired
    private CadastroTarefaDto cadastroTarefa;

    @Autowired
    private CadastroTarefaDto cadastroTarefa1;

    @Autowired
    private Usuario usuario;

    @Autowired
    private Etiqueta etiqueta;

    @Autowired
    private Tarefa tarefa;

    @Autowired
    private Tarefa tarefa1;

    @Autowired
    private AtualizacaoTarefaDto atualizacaoTarefa;

    @Autowired
    private ListagemTarefaDto listagemTarefa;

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
                "Organizar Word",
                "Ordenar tabela clientes no word",
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
    void deveriaRetornarDtoDeListagemDaTarefaQuandoEncontradaPeloId(){

        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));

        assertEquals(listagemTarefa, tarefaService.buscarTarefaPeloId(1L));
    }

    @Test
    void deveriaCairNaExcecaoResourceNotFoundComTarefaNaoEncontradaPeloId(){

        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.buscarTarefaPeloId(1L);
        });

        assertEquals("Tarefa não encontrada!", ex.getMessage());
    }

    @Test
    void deveriaCairNaExcecaoComUsuarioNaoEncontradoAoTentarEncontrarTodasTarefasDeUmUsuario(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.buscarTodasTarefasDeUmUsuario(1L);
        });

        assertEquals("Usuário não encontrado!", ex.getMessage());
    }

    @Test
    void deveriaRetornarListaDeTarefasAoBuscarTodasTarefasDeUmUsuario(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(tarefaRepository.retornarListaDeTarefasPorUsuario(usuario)).thenReturn(List.of(tarefa));

        assertEquals(List.of(listagemTarefa), tarefaService.buscarTodasTarefasDeUmUsuario(1L));
    }

    @Test
    void deveriaRetornarListaDeTarefasPorEtiqueta(){

        List<Tarefa> tarefas = Arrays.asList(tarefa, tarefa1);
        List<ListagemTarefaDto> listagemTarefas = tarefas.stream()
                        .map(ListagemTarefaDto::new)
                        .toList();

        when(etiquetaRepository.retornarEtiquetaComNomeEUsuario("Trabalho", usuario))
                .thenReturn(Collections.singletonList(etiqueta));

        when(tarefaRepository.retornarListaDeTarefasPorEtiqueta(etiqueta))
                .thenReturn(tarefas);

        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.of(usuario));

        assertEquals(listagemTarefas, tarefaService.buscarTodasTarefasPorEtiqueta("Trabalho", 1L));
    }

    @Test
    void deveriaCairNaExcecaoComUsuarioNaoEncontradoAoBuscarTarefaPorEtiqueta(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.buscarTodasTarefasPorEtiqueta("Trabalho", 1L);
        });

        assertEquals("Usuário não encontrado!", ex.getMessage());
    }

    @Test
    void deveriaAcessarMetodoDeCadastrarAoTentarCadastrarTarefa(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(etiquetaRepository.findById(anyLong())).thenReturn(Optional.of(etiqueta));

        tarefaService.criarTarefa(cadastroTarefa);

        then(tarefaRepository).should().save(tarefaCaptor.capture());

        assertEquals(new Tarefa(cadastroTarefa, etiqueta, usuario), tarefaCaptor.getValue());
    }

    @Test
    void deveriaCairNaExcecaoComUsuarioNaoEncontradoAoTentarCadastrarTarefa(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.criarTarefa(cadastroTarefa);
        });

        assertEquals("Usuário não encontrado!", ex.getMessage());
    }

    @Test
    void deveriaCairNaExcecaoComEtiquetaNaoEncontradaAoTentarCadastrarTarefa(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(etiquetaRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.criarTarefa(cadastroTarefa);
        });

        assertEquals("Etiqueta não encontrada!", ex.getMessage());
    }

    @Test
    void deveriaAtualizarOStatusDaTarefaParaConcluido(){

        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));

        tarefaService.concluirTarefa(1L);

        assertEquals(tarefa.getStatus(), Status.FECHADA);

        LocalDateTime agora = LocalDateTime.now();
        assertTrue(tarefa.getDataFinalizacao().isAfter(agora.minusSeconds(1)) &&
                tarefa.getDataFinalizacao().isBefore(agora.plusSeconds(1)));
    }

    @Test
    void deveriaCairNaExcecaoComTarefaNaoEncontradaPeloIdAoTentarConcluir(){

        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.concluirTarefa(1L);
        });

        assertEquals("Tarefa não encontrada!", ex.getMessage());
    }

    @Test
    void deveriaCairNaExcecaoComTarefaJaConcluidaOuExpirada(){

        tarefa.setStatus(Status.EXPIRADA);

        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));

        TaskAlreadyMadeException ex = assertThrows(TaskAlreadyMadeException.class, () -> {
            tarefaService.concluirTarefa(1L);
        });

        assertEquals("Tarefa já concluída!", ex.getMessage());
    }
}