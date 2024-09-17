package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.usuario.ValidacoesUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private List<ValidacoesUsuario> validacoesCadastro;

    public ListagemUsuarioDto retornarUsuario(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        return new ListagemUsuarioDto(usuario);
    }

    public void cadastrarUsuario(CadastroUsuarioDto cadastroUsuario) {
        validacoesCadastro.forEach(v -> v.validar(cadastroUsuario));

        usuarioRepository.save(new Usuario(cadastroUsuario));
    }

    public ListagemUsuarioDto atualizarUsuario(String email, AtualizacaoUsuarioDto atualizacaoUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));



        usuario.atualizarUsuario(atualizacaoUsuario);

        return new ListagemUsuarioDto(usuario);
    }
}
