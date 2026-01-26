package com.project.bcngroup.infraestructure.adapter.repository;

import com.project.bcngroup.infraestructure.adapter.entity.Precio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface IPrecioRepository extends JpaRepository<Precio, Long> {

    Precio findByStartDateAndBrandIdAndProductoId(LocalDateTime startDate, int brandId, int productId);
}
