package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.dto.response.PagamentoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ReservaMapper.class})
public interface PagamentoMapper {

    PagamentoResponse toResponse(Pagamento pagamentoResponse);
}