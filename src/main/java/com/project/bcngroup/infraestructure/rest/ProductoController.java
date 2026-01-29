package com.project.bcngroup.infraestructure.rest;

import com.project.bcngroup.application.service.ProductoService;
import com.project.bcngroup.domain.dto.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RequestMapping("/api/v1/productos")
@AllArgsConstructor
@RestController
@Tag(name = "Productos", description = "Operaciones relacionadas con productos y precios")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener producto", description = "Obtiene un ProductoDto aplicando la fecha de aplicación, id de producto y id de la cadena (brand)")
    public ResponseEntity<ProductoDto> obtenerProductos(
            @Parameter(description = "Fecha de aplicación (por ejemplo: 2020-06-14-10.00.00)", required = true, schema = @Schema(type = "string", example = "2020-06-14T10:00:00"))
            @RequestParam(name = "fechaAplicacion") String fechaAplicacion,

            @Parameter(description = "ID del producto", required = true, example = "35455")
            @RequestParam(name = "productoId") int productoId,

            @Parameter(description = "ID de la cadena / brand", required = true, example = "1")
            @RequestParam(name = "cadenaId") int cadenaId
    ) {
        if (fechaAplicacion.isEmpty() || cadenaId == 0 || productoId == 0) {
            return ResponseEntity.badRequest().build();
        }

        var producto = productoService.obtenerProducto(fechaAplicacion, productoId, cadenaId);
        if(producto == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(producto);
    }
}
