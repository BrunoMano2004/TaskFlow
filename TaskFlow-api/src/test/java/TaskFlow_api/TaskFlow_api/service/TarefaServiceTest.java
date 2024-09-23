package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.AtualizacaoTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

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
    private Usuario usuario;

    @Autowired
    private Etiqueta etiqueta;

    @Autowired
    private Tarefa tarefa;

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

        atualizacaoTarefa = new AtualizacaoTarefaDto(
                "Organizar tabela",
                "Ordenar",
                1L
        );

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
}