package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class ValidarSeUsuarioEMaiorDeIdade implements ValidacoesUsuario{

    @Override
    public void validar(CadastroUsuarioDto cadastroUsuarioDto) {
        Date data = new Date();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(cadastroUsuarioDto.dataNascimento(), formato);
        Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (dataNascimento.after(data)){
            throw new InvalidDataException("Data de nascimento não deve ser maior do que a data atual!");
        } else if ((data.getYear() - dataNascimento.getYear()) < 12) {
            throw new InvalidDataException("A idade mínima para utilização é de 12 anos!");
        }
    }
}
