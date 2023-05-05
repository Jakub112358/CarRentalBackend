package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByBranchOffice_Id(Long id);
    Integer countAllByBranchOffice_IdAndJobPosition(Long id, JobPosition jobPosition);
    List<Employee> findAllByBranchOfficeIsNull();
}
