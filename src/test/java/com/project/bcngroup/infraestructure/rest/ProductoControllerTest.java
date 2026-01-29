package com.project.bcngroup.infraestructure.rest;

import com.project.bcngroup.application.service.ProductoService;
import com.project.bcngroup.domain.dto.ProductoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del ProductoController")
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    private ProductoDto productoDtoTest1;
    private ProductoDto productoDtoTest2;
    private ProductoDto productoDtoTest3;
    private ProductoDto productoDtoTest4;
    private ProductoDto productoDtoTest5;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();

        productoDtoTest1 = new ProductoDto(
                35455,
                1,
                1,
                35.50,
                LocalDateTime.parse("2020-06-14T10:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
        );

        productoDtoTest2 = new ProductoDto(
                35455,
                1,
                1,
                35.50,
                LocalDateTime.parse("2020-06-14T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
        );

        productoDtoTest3 = new ProductoDto(
                35455,
                1,
                1,
                35.50,
                LocalDateTime.parse("2020-06-14T21:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
        );

        productoDtoTest4 = new ProductoDto(
                35455,
                1,
                1,
                35.50,
                LocalDateTime.parse("2020-06-15T10:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
        );

        productoDtoTest5 = new ProductoDto(
                35455,
                1,
                1,
                35.50,
                LocalDateTime.parse("2020-06-16T21:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
        );
    }

    @Test
    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
    void test1ObtenerProductosExitoso() throws Exception {
        String fechaAplicacion = "2020-06-14-10.00.00";
        int productoId = 35455;
        int cadenaId = 1;

        when(productoService.obtenerProducto(fechaAplicacion, productoId, cadenaId))
                .thenReturn(productoDtoTest1);

        mockMvc.perform(get("/api/v1/productos")
                        .param("fechaAplicacion", fechaAplicacion)
                        .param("productoId", String.valueOf(productoId))
                        .param("cadenaId", String.valueOf(cadenaId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(35455))
                .andExpect(jsonPath("$.cadenaId").value(1))
                .andExpect(jsonPath("$.fechaInicio").value("2020-06-14T10:00:00"));

        verify(productoService, times(1)).obtenerProducto(fechaAplicacion, productoId, cadenaId);
    }

    @Test
    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
    void test2ObtenerProductosExitoso() throws Exception {
        String fechaAplicacion = "2020-06-14-16.00.00";
        int productoId = 35455;
        int cadenaId = 1;

        when(productoService.obtenerProducto(fechaAplicacion, productoId, cadenaId))
                .thenReturn(productoDtoTest2);

        mockMvc.perform(get("/api/v1/productos")
                        .param("fechaAplicacion", fechaAplicacion)
                        .param("productoId", String.valueOf(productoId))
                        .param("cadenaId", String.valueOf(cadenaId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(35455))
                .andExpect(jsonPath("$.cadenaId").value(1))
                .andExpect(jsonPath("$.fechaInicio").value("2020-06-14T16:00:00"));

        verify(productoService, times(1)).obtenerProducto(fechaAplicacion, productoId, cadenaId);
    }


    @Test
    @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
    void tes3tObtenerProductosExitoso() throws Exception {
        String fechaAplicacion = "2020-06-14-21.00.00";
        int productoId = 35455;
        int cadenaId = 1;

        when(productoService.obtenerProducto(fechaAplicacion, productoId, cadenaId))
                .thenReturn(productoDtoTest3);

        mockMvc.perform(get("/api/v1/productos")
                        .param("fechaAplicacion", fechaAplicacion)
                        .param("productoId", String.valueOf(productoId))
                        .param("cadenaId", String.valueOf(cadenaId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(35455))
                .andExpect(jsonPath("$.cadenaId").value(1))
                .andExpect(jsonPath("$.fechaInicio").value("2020-06-14T21:00:00"));

        verify(productoService, times(1)).obtenerProducto(fechaAplicacion, productoId, cadenaId);
    }

    @Test
    @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)")
    void test4ObtenerProductosExitoso() throws Exception {
        String fechaAplicacion = "2020-06-15-10.00.00";
        int productoId = 35455;
        int cadenaId = 1;

        when(productoService.obtenerProducto(fechaAplicacion, productoId, cadenaId))
                .thenReturn(productoDtoTest4);

        mockMvc.perform(get("/api/v1/productos")
                        .param("fechaAplicacion", fechaAplicacion)
                        .param("productoId", String.valueOf(productoId))
                        .param("cadenaId", String.valueOf(cadenaId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(35455))
                .andExpect(jsonPath("$.cadenaId").value(1))
                .andExpect(jsonPath("$.fechaInicio").value("2020-06-15T10:00:00"));

        verify(productoService, times(1)).obtenerProducto(fechaAplicacion, productoId, cadenaId);
    }


    @Test
    @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)")
    void test5ObtenerProductosExitoso() throws Exception {
        String fechaAplicacion = "2020-06-16-21.00.00";
        int productoId = 35455;
        int cadenaId = 1;

        when(productoService.obtenerProducto(fechaAplicacion, productoId, cadenaId))
                .thenReturn(productoDtoTest5);

        mockMvc.perform(get("/api/v1/productos")
                        .param("fechaAplicacion", fechaAplicacion)
                        .param("productoId", String.valueOf(productoId))
                        .param("cadenaId", String.valueOf(cadenaId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(35455))
                .andExpect(jsonPath("$.cadenaId").value(1))
                .andExpect(jsonPath("$.fechaInicio").value("2020-06-16T21:00:00"));

        verify(productoService, times(1)).obtenerProducto(fechaAplicacion, productoId, cadenaId);
    }

}

