package com.project.bcngroup.infraestructure.rest;

import com.project.bcngroup.application.service.ProductoService;
import com.project.bcngroup.domain.dto.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/productos")
@AllArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("obtenerProductos")
    public ResponseEntity<ProductoDto> obtenerProductos(
            @RequestParam String fechaAplicacion,
            @RequestParam int productoId,
            @RequestParam int cadenaId
    ) {
       var producto = productoService.obtenerProductos(fechaAplicacion, productoId, cadenaId);
        return ResponseEntity.ok().body(producto);
    }
}
