package TaskFlow_api.TaskFlow_api.validacoes.tarefa;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.DataAlreadyExistException;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidarSeONomeDaTarefaJaNaoExisteTest {

    @InjectMocks
    private ValidarSeONomeDaTarefaJaNaoExiste validacao;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroTarefaDto cadastroTarefa;

    @Autowired
    private Tarefa tarefa;

    @Autowired
    private Usuario usuario;

    @BeforeEach
    void setUp(){

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        usuario = new Usuario(cadastroUsuario);

        CadastroEtiquetaDto cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        Etiqueta etiqueta = new Etiqueta(cadastroEtiqueta, usuario);

        cadastroTarefa = new CadastroTarefaDto(
                "Criar curriculo",
                "Criar curriculo",
                "10/10/2000 22:45",
                1L,
                1L
        );

        tarefa = new Tarefa(cadastroTarefa, etiqueta, usuario);
    }

    @Test
    void deveriaCairNaExcecaoComNomeDaTarefaJaExistente(){

        List<Tarefa> tarefas = List.of(tarefa);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(tarefaRepository.retornarListaDeTarefasPorUsuario(usuario)).thenReturn(tarefas);

        DataAlreadyExistException ex = assertThrows(DataAlreadyExistException.class, () -> {
            validacao.validar(cadastroTarefa);
        });

        assertEquals("Nome da tarefa já existente!", ex.getMessage());
    }

    @Test
    void naoDeveriaCairNaExcecaoComNomeDaTarefaDiferente(){

        tarefa.setNome("Word");

        List<Tarefa> tarefas = List.of(tarefa);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(tarefaRepository.retornarListaDeTarefasPorUsuario(usuario)).thenReturn(tarefas);

        assertDoesNotThrow(() -> {
            validacao.validar(cadastroTarefa);
        });
    }
}