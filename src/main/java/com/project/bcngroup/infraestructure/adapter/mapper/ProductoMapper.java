package com.project.bcngroup.infraestructure.adapter.mapper;

import com.project.bcngroup.domain.model.Producto;
import com.project.bcngroup.infraestructure.adapter.entity.Precio;

public class ProductoMapper {

    public Producto toDomain(Precio domain) {
        Producto producto = new Producto();
        producto.setProductoId(domain.getProductoId());
        producto.setCadenaId(domain.getBrandId());
        producto.setFechaInicio(domain.getStartDate());
        producto.setFechaFin(domain.getEndDate());
        producto.setPriceList(domain.getPriceList());
        producto.setPrecio(domain.getPrice());

        return producto;
    }
}
