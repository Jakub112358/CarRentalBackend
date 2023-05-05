package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.EmployeeDto;
import com.carrentalbackend.model.dto.EmployeeUpdateDto;
import com.carrentalbackend.model.dto.UpdateDto;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EmployeeMapper implements CrudMapper<Employee, EmployeeDto> {
    private final OfficeRepository officeRepository;
    @Override
    public Employee toNewEntity(EmployeeDto dto) {
        BranchOffice office = findOfficeById(dto.getBranchOfficeId());
        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .jobPosition(dto.getJobPosition())
                .branchOffice(office)
                .carPickUps(new ArrayList<>())
                .build();
    }

    @Override
    public UpdateDto toUpdateEntity(EmployeeDto dto) {
        BranchOffice office = findOfficeById(dto.getBranchOfficeId());
        return EmployeeUpdateDto.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .jobPosition(dto.getJobPosition())
                .branchOffice(office)
                .build();
    }

    @Override
    public EmployeeDto toDto(Employee entity) {
        Long officeId = entity.getBranchOffice() != null ? entity.getBranchOffice().getId() : null;
        return EmployeeDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .jobPosition(entity.getJobPosition())
                .branchOfficeId(officeId)
                .build();
    }

    private BranchOffice findOfficeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return officeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id));
        }
    }
}
