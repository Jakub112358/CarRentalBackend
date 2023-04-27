package com.carrentalbackend.service;

import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.repository.OfficeRepository;
import org.springframework.stereotype.Service;

@Service
public class OfficeService extends CrudService<BranchOffice> {
    private final OfficeRepository officeRepository;
    public OfficeService(OfficeRepository repository) {
        super(repository);
        this.officeRepository = repository;
    }
}
