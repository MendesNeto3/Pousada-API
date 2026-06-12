package PousadaAPI.service;

import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.exception.*;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.dtoEntity.QuartoDTO;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import PousadaAPI.repository.QuartoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.*;

@Service
@AllArgsConstructor
@Data
public class QuartoService {

    private final QuartoRepository repository;

    public Object cadastrarQuarto(CriarQuartoRequestDto dto) {
        if (repository.findByNumero(dto.numero()).isPresent()) {
            throw new QuartoJaCadastradoException("O quarto com este número já foi cadastrado.");
        }
        if (dto.precoNoite().compareTo(ZERO) <= 0) {
            throw new ValorQuartoInvalidoException("Valor do quarto inválido");
        }
        Quarto quarto = new Quarto();
        quarto.setNumero(dto.numero());
        quarto.setTipo(dto.tipo());
        quarto.setCapacidade(dto.capacidade());
        quarto.setPrecoNoite(dto.precoNoite());
        quarto.setStatus(StatusQuarto.disponivel);

        return repository.save(quarto);
    }

    public Object atualizarStatusQuarto(QuartoDTO dto) {
        Quarto quarto = repository.findById(dto.id())
                .orElseThrow(() ->
                        new QuartoNaoEncontradoException("O quarto não foi encontrado"));

        if (quarto.getStatus() == StatusQuarto.disponivel) {
            throw new QuartoDisponivelException("O quarto " + dto.numero() + "está disponível");
        }
        if (quarto.getStatus()  == StatusQuarto.ocupado) {
            throw new QuartoIndisponivelException("O quarto" + dto.numero() + "está ocupado");
        }
        if (quarto.getStatus()  == StatusQuarto.indisponivel) {
            throw new QuartoIndisponivelException("O quarto" + dto.numero() + "o quarto está indisponível");
        }
        if (quarto.getStatus()  == StatusQuarto.reservado) {
            throw new QuartoIndisponivelException("O quarto " +dto.numero()+ "está com reserva ativa para esta data.");
        }
        quarto.setStatus(dto.statusQuarto());
        return repository.save(quarto);
    }

    public List<Quarto> listarDisponiveis (LocalDate Checkin, LocalDate Checkout) {
        if (Checkin.isAfter(Checkout)) {
            throw new DataInvalidaException(
                    "Check-in não pode ser efetuado depois do check-out"
            );
        }
        return repository.listarReservasAtivas(Checkin, Checkout);
    }

    public Object excluirQuarto (UUID id) {
        Quarto quarto = repository.findById(id)
                .orElseThrow(()
                        -> new  QuartoNaoEncontradoException(
                                "O quarto não foi encontrado")
                );
        return repository.delete(quarto);
    }
}

