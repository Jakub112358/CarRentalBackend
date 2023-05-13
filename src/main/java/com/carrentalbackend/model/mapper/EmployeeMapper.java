package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.crudDto.EmployeeDto;
import com.carrentalbackend.model.dto.updateDto.EmployeeUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.response.Response;
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
        Office office = findOfficeById(dto.getBranchOfficeId());
        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .jobPosition(dto.getJobPosition())
                .office(office)
                .pickUps(new ArrayList<>())
                .build();
    }

    @Override
    public UpdateDto toUpdateEntity(EmployeeDto dto) {
        Office office = findOfficeById(dto.getBranchOfficeId());
        return EmployeeUpdateDto.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .jobPosition(dto.getJobPosition())
                .office(office)
                .build();
    }

    @Override
    public EmployeeDto toDto(Employee entity) {
        Long officeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return EmployeeDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .jobPosition(entity.getJobPosition())
                .branchOfficeId(officeId)
                .build();
    }

    private Office findOfficeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return officeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id));
        }
    }

    @Override
    public Employee toNewEntity(CreateRequest request) {
        return null;
    }

    @Override
    public Response toResponse(Employee entity) {
        return null;
    }
}
