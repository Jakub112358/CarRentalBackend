package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByOffice_Id(Long id);

    Integer countAllByOffice_IdAndJobPosition(Long id, JobPosition jobPosition);

    List<Employee> findAllByOfficeIsNull();

    Optional<Employee> findByEmail(String username);
}
