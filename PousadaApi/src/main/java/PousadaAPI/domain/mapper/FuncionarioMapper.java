package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.dto.request.CriarFuncionarioRequestDTO;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    Funcionario toEntity (@Valid CriarFuncionarioRequestDTO dto);

    Funcionario toResponse (Funcionario funcionario);

}
