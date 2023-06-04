package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Finances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinancesRepository extends JpaRepository<Finances, Long> {
    Optional<Finances> findFirstByIdIsNotNull();
}
