package PousadaAPI.config;

import PousadaAPI.domain.exception.*;
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

    @ExceptionHandler(HorarioChegadaInvalidoException.class)
    public ResponseEntity<Object> HorarioChegadaInvalidoException (HorarioChegadaInvalidoException ex) {
        return new ResponseEntity<> (ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<Object> RegistroDuplicadoException (RegistroDuplicadoException ex) {
        return new ResponseEntity<> (ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> QuartoJaCadastradoException  (QuartoJaCadastradoException ex) {
        return new ResponseEntity<> (ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> ValorQuartoInvalidoException (ValorQuartoInvalidoException ex) {
        return new ResponseEntity<> (ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
