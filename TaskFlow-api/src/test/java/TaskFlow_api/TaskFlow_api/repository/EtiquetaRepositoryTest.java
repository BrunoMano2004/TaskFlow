package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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

    @Test
    void deveriaRetornarEtiquetaComNomeEEmailDoUsuarioCorretos(){

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuario = new Usuario(cadastroUsuario);

        CadastroEtiquetaDto cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        Etiqueta etiquetaExpec = new Etiqueta(cadastroEtiqueta, usuario);

        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiquetaExpec);

        Etiqueta etiquetaResul =
                etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Trabalho", "email@email.com").get();

        assertEquals(etiquetaExpec, etiquetaResul);
    }

    @Test
    void naoDeveriaRetornarEtiquetaComEmailDoUsuarioIncorreto(){

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuario = new Usuario(cadastroUsuario);

        CadastroEtiquetaDto cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        Etiqueta etiquetaExpec = new Etiqueta(cadastroEtiqueta, usuario);

        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiquetaExpec);

        Optional<Etiqueta> etiquetaResul =
                etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Trabalho", "emailemail@email.com");

        assertTrue(etiquetaResul.isEmpty());
    }

    @Test
    void naoDeveriaRetornarEtiquetaComNomeErrado(){

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuario = new Usuario(cadastroUsuario);

        CadastroEtiquetaDto cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        Etiqueta etiquetaExpec = new Etiqueta(cadastroEtiqueta, usuario);

        usuarioRepository.save(usuario);

        etiquetaRepository.save(etiquetaExpec);

        Optional<Etiqueta> etiquetaResul =
                etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Casa", "email@email.com");

        assertTrue(etiquetaResul.isEmpty());
    }

}