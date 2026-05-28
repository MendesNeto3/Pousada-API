package PousadaAPI.dto.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public record dtoErroResposta(int status, String mensagem, List<dtoErroCampo> erros) {

    public static dtoErroResposta respostapadrao (String mensagem ) {
        return new dtoErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static dtoErroResposta conflito (String mensagem) {
        return new dtoErroResposta(HttpStatus.NO_CONTENT.value(), mensagem, List.of());
    }
}
