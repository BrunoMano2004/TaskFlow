package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.DataAlreadyExistException;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarEmailJaCadastrado implements ValidacoesUsuario{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(CadastroUsuarioDto cadastroUsuarioDto) {
        usuarioRepository.findByEmail(cadastroUsuarioDto.email()).ifPresent(exception -> {
            throw new DataAlreadyExistException("Email já cadastrado!");
        });
    }
}
