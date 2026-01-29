package com.project.bcngroup.application.mapper;

import com.project.bcngroup.domain.dto.ProductoDto;
import com.project.bcngroup.domain.model.Producto;

public class ProductoDtoMapper {

    public ProductoDto toDto(Producto domain) {
        ProductoDto dto = new ProductoDto();
        dto.setProductoId(domain.getProductoId());
        dto.setCadenaId(domain.getCadenaId());
        dto.setFechaInicio(domain.getFechaInicio());
        dto.setFechaFin(domain.getFechaFin());
        dto.setPriceList(domain.getPriceList());
        dto.setPrecio(domain.getPrecio());

        return dto;
    }
}
