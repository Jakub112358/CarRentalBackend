package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.OfficeDto;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.mapper.OfficeMapper;
import com.carrentalbackend.repository.OfficeRepository;
import org.springframework.stereotype.Service;

@Service
public class OfficeService extends CrudService<BranchOffice, OfficeDto> {
    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository repository, OfficeMapper officeMapper) {
        super(repository, officeMapper);
        this.officeRepository = repository;
    }

    @Override
    public OfficeDto update(Long id, OfficeDto requestDto) {
        BranchOffice office = officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (requestDto.getAddress() != null)
            office.setAddress(requestDto.getAddress());
        return mapper.toDto(office);
    }

    @Override
    public void deleteById(Long id) {
        BranchOffice office = officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        nullEmployees(office);
        nullCars(office);
        nullPickUpReservations(office);
        nullReturnReservations(office);
        officeRepository.deleteById(id);
    }

    private void nullReturnReservations(BranchOffice office) {
        office.getReturnReservations().forEach(r -> r.setReturnOffice(null));
    }

    private void nullPickUpReservations(BranchOffice office) {
        office.getPickUpReservations().forEach(r -> r.setPickUpOffice(null));
    }

    private void nullCars(BranchOffice office) {
        office.getAvailableCars().forEach(c -> c.setCurrentBranchOffice(null));
    }

    private void nullEmployees(BranchOffice office) {
        office.getEmployees().forEach(e -> e.setBranchOffice(null));
    }

}
