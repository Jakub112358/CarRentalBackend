package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<BranchOffice, Long> {
}
