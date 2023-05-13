package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.updateDto.EmployeeUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.create.EmployeeCreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.EmployeeResponse;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EmployeeMapper implements CrudMapper<Employee> {
    private final OfficeRepository officeRepository;

    private Office findOfficeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    @Override
    public Employee toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, EmployeeCreateRequest.class);
        EmployeeCreateRequest employeeRequest = (EmployeeCreateRequest) request;

        Office office = findOfficeById(employeeRequest.getBranchOfficeId());
        return Employee.builder()
                .id(employeeRequest.getId())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .jobPosition(employeeRequest.getJobPosition())
                .office(office)
                .pickUps(new ArrayList<>())
                .build();
    }

    @Override
    public Response toResponse(Employee entity) {
        Long officeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return EmployeeResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .jobPosition(entity.getJobPosition())
                .officeId(officeId)
                .build();
    }

    @Override
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, EmployeeCreateRequest.class);
        EmployeeCreateRequest employeeRequest = (EmployeeCreateRequest) request;

        Office office = findOfficeById(employeeRequest.getBranchOfficeId());
        return EmployeeUpdateDto.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .jobPosition(employeeRequest.getJobPosition())
                .office(office)
                .build();
    }
}
