package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.*;
import PousadaAPI.dto.response.ResponseDto;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",
        uses = {
                HospedeMapper.class,
                QuartoMapper.class
        }
)
public interface ResponseMapper {
  ResponseDto toDto(Reserva reserva);

  ResponseDto toResponse (Hospede hospede);

  ResponseDto toResponse (Quarto quarto);

  Pagamento toDTO (Pagamento pagamento);

  Pagamento toDTO(BigDecimal saldoAberto);

  Funcionario toResponse(Funcionario funcionario);
}
