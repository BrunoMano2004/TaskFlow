package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveriaRetornarUsuarioPeloEmail(){
        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuarioSalvo = new Usuario(cadastroUsuario);

        usuarioRepository.save(usuarioSalvo);

        Usuario resul = usuarioRepository.findByEmail(usuarioSalvo.getEmail()).orElse(null);

        assertThat(resul).isNotNull();
        assertEquals(usuarioSalvo, resul);
    }
}