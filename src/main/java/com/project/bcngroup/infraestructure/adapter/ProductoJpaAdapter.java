package com.project.bcngroup.infraestructure.adapter;

import com.project.bcngroup.domain.model.Producto;
import com.project.bcngroup.domain.port.IProductoPort;
import com.project.bcngroup.infraestructure.adapter.mapper.ProductoMapper;
import com.project.bcngroup.infraestructure.adapter.repository.IPrecioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.project.bcngroup.infraestructure.commons.Utils.formatearFecha;

@Repository
@AllArgsConstructor
public class ProductoJpaAdapter implements IProductoPort {

    private final IPrecioRepository precioRepository;

    @Override
    public Producto obtenerProducto(String fechaAplicacion, int productoId, int cadenaId) {

        var mapper = new ProductoMapper();
        var fechaFormateada = formatearFecha(fechaAplicacion);

        var precioEntity = precioRepository.findByStartDateAndBrandIdAndProductoId(fechaFormateada, cadenaId, productoId);

        return mapper.toDomain(precioEntity);
    }
}
