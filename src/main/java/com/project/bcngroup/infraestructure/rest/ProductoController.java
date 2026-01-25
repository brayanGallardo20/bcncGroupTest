package com.project.bcngroup.infraestructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/productos")
public class ProductoController {

    @GetMapping("obtenerProductos")
    public ResponseEntity<String> obtenerProductos(
            @RequestParam String fechaAplicacion,
            @RequestParam String productoId,
            @RequestParam String cadenaId
    ) {
        return ResponseEntity.ok("Lista de productos");
    }
}
