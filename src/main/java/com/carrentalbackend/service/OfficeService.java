package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.dto.OfficeDto;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.mapper.OfficeMapper;
import com.carrentalbackend.repository.OfficeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfficeService extends CrudService<BranchOffice, OfficeDto> {
    private final OfficeRepository officeRepository;
    public OfficeService(OfficeRepository repository, OfficeMapper officeMapper) {
        super(repository,officeMapper);
        this.officeRepository = repository;
    }

    //TODO use proper exception
    @Override
    public void update(Long id, OfficeDto requestDto) {
        BranchOffice office = officeRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (requestDto.getAddress() != null)
            office.setAddress(requestDto.getAddress());
    }

    @Override
    public void deleteById(Long id) {
        BranchOffice office = officeRepository.findById(id).orElseThrow(()-> new RuntimeException());
        nullEmployees(office);
        nullCars(office);
        nullPickUpReservations(office);
        nullReturnReservations(office);
        officeRepository.deleteById(id);
    }

    private void nullReturnReservations(BranchOffice office) {
        office.getReturnReservations().forEach(r->r.setReturnOffice(null));
    }

    private void nullPickUpReservations(BranchOffice office) {
        office.getPickUpReservations().forEach(r->r.setPickUpOffice(null));
    }

    private void nullCars(BranchOffice office) {
        office.getAvailableCars().forEach(c->c.setCurrentBranchOffice(null));
    }

    private void nullEmployees(BranchOffice office) {
        office.getEmployees().forEach(e->e.setBranchOffice(null));
    }

}
