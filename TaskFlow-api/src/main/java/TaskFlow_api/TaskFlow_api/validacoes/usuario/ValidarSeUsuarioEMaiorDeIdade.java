package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ValidarSeUsuarioEMaiorDeIdade implements ValidacoesUsuario<CadastroUsuarioDto>{

    @Override
    public void validar(CadastroUsuarioDto cadastroUsuarioDto) {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(cadastroUsuarioDto.dataNascimento(), formato);

        if (dataNascimento.isAfter(dataAtual)){
            throw new InvalidDataException("Data de nascimento não deve ser maior do que a data atual!");
        } else if ((dataAtual.getYear() - dataNascimento.getYear()) < 12) {
            throw new InvalidDataException("A idade mínima para utilização é de 12 anos!");
        }
    }
}
