package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.mapper.CompanyMapper;
import com.carrentalbackend.repository.CompanyRepository;
import org.springframework.stereotype.Service;


@Service
public class CompanyService extends CrudService<Company, CompanyDto> {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        super(companyRepository, companyMapper);
        this.companyRepository = companyRepository;
    }


    @Override
    public void deleteById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        nullBranchOffices(company);
        companyRepository.deleteById(id);
    }

    private void nullBranchOffices(Company company) {
        company.getBranchOffices().forEach((bo) -> bo.setCompany(null));
    }

}
