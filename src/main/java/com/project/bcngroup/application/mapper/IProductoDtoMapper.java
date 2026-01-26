package com.project.bcngroup.application.mapper;

import com.project.bcngroup.domain.dto.ProductoDto;
import com.project.bcngroup.domain.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductoDtoMapper {

    @Mapping(source = "productoId", target = "productoId")
    @Mapping(source = "brandId", target = "brandId")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "priceList", target = "priceList")
    @Mapping(source = "price", target = "price")
    ProductoDto toDto(Producto domain);

}
