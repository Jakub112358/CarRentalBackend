package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Pricelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricelistRepository extends JpaRepository<Pricelist, Long> {
}
