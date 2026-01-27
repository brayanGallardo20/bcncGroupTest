package com.project.bcngroup.infraestructure.commons;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static final String PATTERN_FECHA = "yyyy-MM-dd-HH.mm.ss";

    public static LocalDateTime formatearFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FECHA);

        return LocalDateTime.parse(fecha, formatter);
    }

}
