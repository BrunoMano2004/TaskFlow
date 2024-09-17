package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.DataAlreadyExistException;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarAtualizacaoEmailJaCadastrado implements ValidacoesUsuario<AtualizacaoUsuarioDto>{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(AtualizacaoUsuarioDto atualizacaoUsuario) {
        if (!(atualizacaoUsuario.email() == null)){
            usuarioRepository.findByEmail(atualizacaoUsuario.email()).ifPresent(exception -> {
                throw new DataAlreadyExistException("Email já cadastrado!");
            });
        }
    }
}
