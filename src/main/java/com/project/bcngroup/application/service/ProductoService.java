package com.project.bcngroup.application.service;

import com.project.bcngroup.application.mapper.IProductoDtoMapper;
import com.project.bcngroup.application.usecase.IProductoUseCase;
import com.project.bcngroup.domain.dto.ProductoDto;
import com.project.bcngroup.infraestructure.adapter.ProductoJpaAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductoService implements IProductoUseCase {

    private final ProductoJpaAdapter productoJpaAdapter;
    private final IProductoDtoMapper productoDtoMapper;

    @Override
    public ProductoDto obtenerProductos(String fechaAplicacion, int productoId, int cadenaId) {

        var producto = productoJpaAdapter.obtenerProductos(fechaAplicacion, productoId, cadenaId);
        return productoDtoMapper.toDto(producto);
    }

}
