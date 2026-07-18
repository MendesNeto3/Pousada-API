package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.dto.request.CriarPagamentoRequest;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {ReservaMapper.class})
public interface PagamentoMapper {

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping (target = "statusPagamento", ignore = true)
    Pagamento toEntity(@Valid CriarPagamentoRequest dto);

    Pagamento toResponse (BigDecimal pagamento);

    Pagamento toResponse(Pagamento pagamento);
}
