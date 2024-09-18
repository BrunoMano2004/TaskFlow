package TaskFlow_api.TaskFlow_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandles {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> tratarRecursosNaoEncontrados(ResourceNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<String> tratarDadosDuplicados(DataAlreadyExistException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> tratarDadosInválidos(InvalidDataException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroBeanValidation>> tratarDadosInvalidosBeanValidation(MethodArgumentNotValidException ex){
        List<ErroBeanValidation> erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(ErroBeanValidation::new)
                .toList();

        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UrlNotValidException.class)
    public ResponseEntity<String> tratarUrlInvalida(UrlNotValidException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public record ErroBeanValidation(
            String campo,
            String mensagem
    ){
        public ErroBeanValidation(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
