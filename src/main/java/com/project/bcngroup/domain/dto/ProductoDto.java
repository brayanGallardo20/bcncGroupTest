package com.project.bcngroup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoDto {

    private int productoId;
    private int cadenaId;
    private int priceList;
    private double precio;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;


}
