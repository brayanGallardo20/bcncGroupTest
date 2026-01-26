package com.project.bcngroup.infraestructure.adapter;

import com.project.bcngroup.domain.model.Producto;
import com.project.bcngroup.domain.port.IProductoPort;
import com.project.bcngroup.infraestructure.adapter.mapper.IProductoMapper;
import com.project.bcngroup.infraestructure.adapter.repository.IPrecioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class ProductoJpaAdapter implements IProductoPort {

    private final IPrecioRepository precioRepository;
    private final IProductoMapper productoMapper;

    @Override
    public Producto obtenerProductos(String fechaAplicacion, int productoId, int cadenaId) {

        var precioEntity = precioRepository.findByStartDateAndBrandIdAndProductoId(LocalDateTime.now(), productoId, cadenaId);

        return productoMapper.toDomain(precioEntity);
    }
}
