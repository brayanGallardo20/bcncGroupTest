package com.project.bcngroup.application.usecase;

import com.project.bcngroup.domain.dto.ProductoDto;

public interface IProductoUseCase {

    ProductoDto obtenerProducto(String fechaAplicacion, int productoId, int cadenaId);
}
