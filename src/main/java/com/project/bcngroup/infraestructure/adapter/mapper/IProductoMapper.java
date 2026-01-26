package com.project.bcngroup.infraestructure.adapter.mapper;

import com.project.bcngroup.domain.model.Producto;
import com.project.bcngroup.infraestructure.adapter.entity.Precio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductoMapper {

    @Mapping(source = "productoId", target = "productoId")
    @Mapping(source = "brandId", target = "brandId")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "priceList", target = "priceList")
    @Mapping(source = "price", target = "price")
    Producto toDomain(Precio domain);

}
