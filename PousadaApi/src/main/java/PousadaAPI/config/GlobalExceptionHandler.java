package PousadaAPI.config;

import PousadaAPI.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            HospedeNaoEncontradoException.class,
            PagamentoNaoEncontradoException.class,
            QuartoNaoEncontradoException.class,
            ReservaNaoEncontradaException.class
    })
    public ProblemDetail handleNotFoundExceptions(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problemDetail.setTitle("Recurso não encontrado");
        problemDetail.setType(URI.create("https://api.seuhotel.com/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({
            QuartoJaCadastradoException.class,
            RegistroDuplicadoException.class,
            ReservaJaRealizadaException.class
    })
    public ProblemDetail handleConflictExceptions(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
        problemDetail.setTitle("Conflito de estado do recurso");
        problemDetail.setType(URI.create("https://api.seuhotel.com/errors/conflict"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({
            CadastroIndisponivelException.class,
            CheckinAntecipadoException.class,
            DadosInvalidosException.class,
            DataInvalidaException.class,
            HorarioChegadaInvalidoException.class,
            QuartoDisponivelException.class,
            QuartoIndisponivelException.class,
            ReservaNaoDisponivelException.class,
            ValorQuartoInvalidoException.class
    })
    public ProblemDetail handleBadRequestExceptions(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problemDetail.setTitle("Regra de negócio violada");
        problemDetail.setType(URI.create("https://api.seuhotel.com/errors/bad-request"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado no servidor."
        );
        problemDetail.setTitle("Erro interno no servidor");
        problemDetail.setType(URI.create("https://api.seuhotel.com/errors/internal-server-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}

