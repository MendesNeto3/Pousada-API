package PousadaAPI.config;

import PousadaAPI.domain.exception.CadastroIndisponivelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CadastroIndisponivelException.class)
    public ResponseEntity<Object> CadastroIndisponivelException (CadastroIndisponivelException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
