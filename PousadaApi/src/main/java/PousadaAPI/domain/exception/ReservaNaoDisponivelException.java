package PousadaAPI.domain.exception;

public class ReservaNaoDisponivelException extends RuntimeException {
    public ReservaNaoDisponivelException(String message) {
        super(message);
    }
}
