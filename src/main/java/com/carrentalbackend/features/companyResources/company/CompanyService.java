package com.carrentalbackend.features.companyResources.company;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.repository.CompanyRepository;
import org.springframework.stereotype.Service;


@Service
public class CompanyService extends CrudService<Company, CompanyUpdateRequest, CompanyCreateRequest> {
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
        company.getOffices().forEach((bo) -> bo.setCompany(null));
    }

}
