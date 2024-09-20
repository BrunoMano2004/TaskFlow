package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EtiquetaRepositoryTest {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @MockBean
    private Etiqueta etiqueta;

    @Autowired
    private Etiqueta etiqueta1;

    @MockBean
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

        CadastroEtiquetaDto cadastroEtiqueta1 = new CadastroEtiquetaDto(
                "Casa",
                "#536578",
                1L
        );

        etiqueta = new Etiqueta(cadastroEtiqueta, usuario);
        etiqueta1 = new Etiqueta(cadastroEtiqueta1, usuario);
    }

    @Test
    void deveriaRetornarEtiquetaComNomeEEmailDoUsuarioCorretos(){

        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiqueta);

        Etiqueta etiquetaResul =
                etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Trabalho", "email@email.com").get();

        assertEquals(etiqueta, etiquetaResul);
    }

    @Test
    void naoDeveriaRetornarEtiquetaComEmailDoUsuarioIncorreto(){

        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiqueta);

        Optional<Etiqueta> etiquetaResul =
                etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Trabalho", "emailemail@email.com");

        assertTrue(etiquetaResul.isEmpty());
    }

    @Test
    void naoDeveriaRetornarEtiquetaComNomeErrado(){

        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiqueta);

        Optional<Etiqueta> etiquetaResul =
                etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Casa", "email@email.com");

        assertTrue(etiquetaResul.isEmpty());
    }

    @Test
    void deveriaRetornarTodasAsEtiquetasDeUmUsuario(){
        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiqueta);
        etiquetaRepository.save(etiqueta1);

        List<Etiqueta> etiquetas = Arrays.asList(etiqueta, etiqueta1);

        assertEquals(etiquetas, etiquetaRepository.retornarEtiquetaPorUsuario(usuario.getId()));
    }

    @Test
    void deveriaRetornarAsEtiquetasDeSomenteUmUsuario(){

        CadastroUsuarioDto cadastroUsuario1 = new CadastroUsuarioDto(
                "emailemail@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuario1 = new Usuario(cadastroUsuario1);

        CadastroEtiquetaDto cadastroEtiqueta = new CadastroEtiquetaDto(
                "Casa",
                "#536578",
                1L
        );

        Etiqueta etiqueta2 = new Etiqueta(cadastroEtiqueta, usuario1);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiqueta2);
        etiquetaRepository.save(etiqueta);
        etiquetaRepository.save(etiqueta1);

        List<Etiqueta> etiquetas = Arrays.asList(etiqueta, etiqueta1);

        assertEquals(etiquetas, etiquetaRepository.retornarEtiquetaPorUsuario(usuario.getId()));
    }
}