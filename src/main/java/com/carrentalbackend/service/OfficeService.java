package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.crudDto.OfficeDto;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.mapper.OfficeMapper;
import com.carrentalbackend.repository.OfficeRepository;
import org.springframework.stereotype.Service;

@Service
public class OfficeService extends CrudService<Office, OfficeDto> {
    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository repository, OfficeMapper officeMapper) {
        super(repository, officeMapper);
        this.officeRepository = repository;
    }



    @Override
    public void deleteById(Long id) {
        Office office = officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        nullOfficeInEmployees(office);
        nullOfficeInCars(office);
        nullPickUpOfficeInReservations(office);
        nullReturnOfficeInReservations(office);
        officeRepository.deleteById(id);
    }

    private void nullReturnOfficeInReservations(Office office) {
        office.getReturnReservations().forEach(r -> r.setReturnOffice(null));
    }

    private void nullPickUpOfficeInReservations(Office office) {
        office.getPickUpReservations().forEach(r -> r.setPickUpOffice(null));
    }

    private void nullOfficeInCars(Office office) {
        office.getAvailableCars().forEach(c -> c.setCurrentOffice(null));
    }

    private void nullOfficeInEmployees(Office office) {
        office.getEmployees().forEach(e -> e.setOffice(null));
    }

}
