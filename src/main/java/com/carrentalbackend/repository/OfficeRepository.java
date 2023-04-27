package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<BranchOffice, Long> {
}
