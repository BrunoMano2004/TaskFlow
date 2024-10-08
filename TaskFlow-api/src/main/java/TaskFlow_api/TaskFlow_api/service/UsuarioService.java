package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.usuario.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Login;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.LoginReposiory;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.usuario.ValidacoesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LoginReposiory loginReposiory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private List<ValidacoesUsuario<CadastroUsuarioDto>> validacoesUsuarioCadastro;

    @Autowired
    private List<ValidacoesUsuario<AtualizacaoUsuarioDto>> validacoesUsuarioAtualizacao;

    public void cadastrarUsuario(CadastroUsuarioDto cadastroUsuario) {
        validacoesUsuarioCadastro.forEach(v -> v.validar(cadastroUsuario));

        Usuario usuario = new Usuario(cadastroUsuario);

        usuarioRepository.save(usuario);
        loginReposiory.save(new Login(cadastroUsuario.email(), passwordEncoder.encode(cadastroUsuario.senha()), usuario));
    }

    public ListagemUsuarioDto atualizarUsuario(AtualizacaoUsuarioDto atualizacaoUsuario) {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        validacoesUsuarioAtualizacao.forEach(v -> v.validar(atualizacaoUsuario));

        usuario.atualizarUsuario(atualizacaoUsuario);

        return new ListagemUsuarioDto(usuario);
    }

    public void deletarUsuario() {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        usuarioRepository.delete(usuario);
    }

    public ListagemUsuarioDto retornarUsuario() {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        return new ListagemUsuarioDto(usuario);
    }
}
