package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.estatistica.DadosPeriodoDto;
import TaskFlow_api.TaskFlow_api.dto.estatistica.EstatisticaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.DataAlreadyExistException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.estatistica.ValidacoesEstatistica;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstatisticaServiceTest {

    @InjectMocks
    private EstatisticaService estatisticaService;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Spy
    private List<ValidacoesEstatistica> validacoes = new ArrayList<>();

    @Autowired
    private DadosPeriodoDto dadosPeriodo;

    @Autowired
    private CadastroUsuarioDto cadastroUsuario;

    @Autowired
    private Usuario usuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    @BeforeEach
    void setUp(){

        dadosPeriodo = new DadosPeriodoDto(
                1L,
                "20/10/2020",
                "20/10/2023"
        );

        cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario usuario",
                "10/10/2000",
                "imagem"
        );

        usuario = new Usuario(cadastroUsuario);
    }

    @Test
    void deveriaRetornarEstatisticasDeUmUsuarioEmRelacaoAsTarefasExpiradasOuFechadas(){

        EstatisticaDto estatisticas = new EstatisticaDto(
                "20/10/2020",
                "20/10/2023",
                5L,
                5L,
                50,
                50
        );

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(tarefaRepository
                .retornaNumeroDeTarefasConluidasNoPeriodo(
                        usuario,
                        LocalDateTime.parse("20/10/2020 00:00", dtf),
                        LocalDateTime.parse("20/10/2023 00:00", dtf))).thenReturn(5L);

        when(tarefaRepository
                .retornaNumeroDeTarefasExpiradasNoPeriodo(
                        usuario,
                        LocalDateTime.parse("20/10/2020 00:00", dtf),
                        LocalDateTime.parse("20/10/2023 00:00", dtf))).thenReturn(5L);

        when(tarefaRepository
                .retornaNumeroDeTarefasNoPeriodo(
                        usuario,
                        LocalDateTime.parse("20/10/2020 00:00", dtf),
                        LocalDateTime.parse("20/10/2023 00:00", dtf))).thenReturn(10L);

        assertEquals(estatisticas, estatisticaService.gerarEstatisticasPorPeriodo(dadosPeriodo));

    }
}