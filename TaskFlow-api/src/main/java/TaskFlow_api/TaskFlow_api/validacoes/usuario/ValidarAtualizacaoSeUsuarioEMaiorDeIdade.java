package TaskFlow_api.TaskFlow_api.validacoes.usuario;


import TaskFlow_api.TaskFlow_api.dto.usuario.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ValidarAtualizacaoSeUsuarioEMaiorDeIdade implements ValidacoesUsuario<AtualizacaoUsuarioDto> {

    @Override
    public void validar(AtualizacaoUsuarioDto atualizacaoUsuario) {
        if (!(atualizacaoUsuario.dataNascimento() == null)){
            LocalDate dataAtual = LocalDate.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(atualizacaoUsuario.dataNascimento(), formato);

            if (dataNascimento.isAfter(dataAtual)){
                throw new InvalidDataException("Data de nascimento não deve ser maior do que a data atual!");
            } else if ((dataAtual.getYear() - dataNascimento.getYear()) < 12) {
                throw new InvalidDataException("A idade mínima para utilização é de 12 anos!");
            }
        }
    }
}
