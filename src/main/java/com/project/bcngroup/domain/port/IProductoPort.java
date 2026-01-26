package com.project.bcngroup.domain.port;

import com.project.bcngroup.domain.model.Producto;

public interface IProductoPort {

    Producto obtenerProductos(String fechaAplicacion, int productoId, int cadenaId);
}
