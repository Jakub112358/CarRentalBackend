package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.crudDto.OfficeDto;
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
    public void deleteById(Long id) {
        BranchOffice office = officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        nullOfficeInEmployees(office);
        nullOfficeInCars(office);
        nullPickUpOfficeInReservations(office);
        nullReturnOfficeInReservations(office);
        officeRepository.deleteById(id);
    }

    private void nullReturnOfficeInReservations(BranchOffice office) {
        office.getReturnReservations().forEach(r -> r.setReturnOffice(null));
    }

    private void nullPickUpOfficeInReservations(BranchOffice office) {
        office.getPickUpReservations().forEach(r -> r.setPickUpOffice(null));
    }

    private void nullOfficeInCars(BranchOffice office) {
        office.getAvailableCars().forEach(c -> c.setCurrentBranchOffice(null));
    }

    private void nullOfficeInEmployees(BranchOffice office) {
        office.getEmployees().forEach(e -> e.setBranchOffice(null));
    }

}
