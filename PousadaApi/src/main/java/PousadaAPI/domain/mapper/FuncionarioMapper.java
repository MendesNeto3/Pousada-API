package PousadaAPI.domain.mapper;
import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.dto.response.FuncionarioResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    FuncionarioResponse toResponse (Funcionario funcionario);

}
