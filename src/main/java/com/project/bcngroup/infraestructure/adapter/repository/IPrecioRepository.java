package com.project.bcngroup.infraestructure.adapter.repository;

import com.project.bcngroup.infraestructure.adapter.entity.Precio;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IPrecioRepository extends JpaRepository<Precio, Long> {

    Precio findByStartDateAndBrandIdAndProductoId(LocalDateTime startDate, int brandId, int productId);
}
