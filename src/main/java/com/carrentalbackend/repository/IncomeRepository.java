package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByReservation_Id(Long reservationId);
}
