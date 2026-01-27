package com.project.bcngroup.infraestructure.rest;

import com.project.bcngroup.application.service.ProductoService;
import com.project.bcngroup.domain.dto.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/productos")
@AllArgsConstructor
@RestController
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<ProductoDto> obtenerProductos(
            @RequestParam(name = "fechaAplicacion") String fechaAplicacion,
            @RequestParam(name = "productoId") int productoId,
            @RequestParam(name = "cadenaId") int cadenaId
    ) {
        var producto = productoService.obtenerProducto(fechaAplicacion, productoId, cadenaId);
        return ResponseEntity.ok().body(producto);
    }
}
