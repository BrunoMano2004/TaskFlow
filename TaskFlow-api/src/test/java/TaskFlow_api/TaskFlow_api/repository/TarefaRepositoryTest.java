package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.AtualizacaoTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TarefaRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @MockBean
    private CadastroUsuarioDto cadastroUsuario;

    @Autowired
    private CadastroUsuarioDto cadastroUsuario1;

    @MockBean
    private Usuario usuario;

    @Autowired
    private Usuario usuario1;

    @MockBean
    private CadastroEtiquetaDto cadastroEtiqueta;

    @MockBean
    private Etiqueta etiqueta;

    @Autowired
    private Etiqueta etiqueta1;

    @MockBean
    private CadastroTarefaDto cadastroTarefa;

    @Autowired
    private CadastroTarefaDto cadastroTarefa2;

    @Autowired
    private CadastroTarefaDto cadastroTarefa1;

    @MockBean
    private Tarefa tarefa;

    @Autowired
    private Tarefa tarefa1;

    @Autowired
    private Tarefa tarefa2;

    @BeforeEach
    void setUp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        cadastroUsuario1 = new CadastroUsuarioDto(
                "emailemail@email.com",
                "Usuario",
                "10/10/2002",
                "imagem"
        );

        usuario1 = new Usuario(cadastroUsuario1);

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

        cadastroTarefa2 = new CadastroTarefaDto(
                "Arrumar arquivo word",
                "Arquivo word",
                LocalDateTime.now().plusDays(2L).format(dtf),
                1L,
                1L
        );

        tarefa2 = new Tarefa(cadastroTarefa2, etiqueta, usuario1);
        tarefa1 = new Tarefa(cadastroTarefa1, etiqueta, usuario);
        tarefa = new Tarefa(cadastroTarefa, etiqueta, usuario);
    }

    @Test
    void deveriaRetonarListaDeTarefasDeUmUsuario(){

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario1);

        etiqueta = new Etiqueta("Trabalho", "#940534", usuario);

        etiquetaRepository.save(etiqueta);

        tarefa.setUsuario(usuario);
        tarefa.setEtiqueta(etiqueta);
        tarefa1.setUsuario(usuario);
        tarefa1.setEtiqueta(etiqueta);
        tarefa2.setUsuario(usuario1);
        tarefa2.setEtiqueta(etiqueta);

        tarefaRepository.save(tarefa);
        tarefaRepository.save(tarefa1);
        tarefaRepository.save(tarefa2);

        List<Tarefa> tarefas = Arrays.asList(tarefa, tarefa1);

        assertEquals(tarefas, tarefaRepository.retornarListaDeTarefasPorUsuario(usuario));

    }

    @Test
    void deveriaRetornarListaDeTarefasPorEtiqueta(){

        usuarioRepository.save(usuario);

        etiqueta = new Etiqueta("Trabalho", "#940534", usuario);
        etiqueta1 = new Etiqueta("Casa", "#frgrsd", usuario);

        etiquetaRepository.save(etiqueta);
        etiquetaRepository.save(etiqueta1);

        tarefa.setEtiqueta(etiqueta);
        tarefa1.setEtiqueta(etiqueta);
        tarefa2.setEtiqueta(etiqueta1);

        tarefa.setUsuario(usuario);
        tarefa1.setUsuario(usuario);
        tarefa2.setUsuario(usuario);

        tarefaRepository.save(tarefa);
        tarefaRepository.save(tarefa1);
        tarefaRepository.save(tarefa2);

        List<Tarefa> tarefasExpec = Arrays.asList(tarefa, tarefa1);
        List<Tarefa> tarefasResul = tarefaRepository.retornarListaDeTarefasPorEtiqueta(etiqueta);

        assertEquals(tarefasExpec, tarefasResul);
    }
}