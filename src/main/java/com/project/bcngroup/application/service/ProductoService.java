package com.project.bcngroup.application.service;

import com.project.bcngroup.application.mapper.ProductoDtoMapper;
import com.project.bcngroup.application.usecase.IProductoUseCase;
import com.project.bcngroup.domain.dto.ProductoDto;
import com.project.bcngroup.infraestructure.adapter.ProductoJpaAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductoService implements IProductoUseCase {

    private final ProductoJpaAdapter productoJpaAdapter;

    @Override
    public ProductoDto obtenerProducto(String fechaAplicacion, int productoId, int cadenaId) {

        var mapper = new ProductoDtoMapper();
        var producto = productoJpaAdapter.obtenerProducto(fechaAplicacion, productoId, cadenaId);

        return mapper.toDto(producto);
    }

}
