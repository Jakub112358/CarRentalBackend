package com.carrentalbackend.features.companyResources.companies;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.companyResources.companies.rest.CompanyMapper;
import com.carrentalbackend.features.companyResources.companies.rest.CompanyRequest;
import com.carrentalbackend.features.companyResources.companies.rest.CompanyUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.repository.CompanyRepository;
import org.springframework.stereotype.Service;


@Service
public class CompanyService extends CrudService<Company, CompanyRequest> {
    private final CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, CompanyUpdateTool companyUpdateTool) {
        super(companyRepository, companyMapper, companyUpdateTool);
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
