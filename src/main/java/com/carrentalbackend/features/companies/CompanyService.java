package com.carrentalbackend.features.companies;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.companies.rest.CompanyMapper;
import com.carrentalbackend.features.companies.rest.CompanyRequest;
import com.carrentalbackend.features.companies.rest.CompanyUpdateTool;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyUpdateTool updateTool;


    public Response findCompany() {
        Company company = getCompanyFromDB();
        return companyMapper.toResponse(company);
    }

    public Response update(CompanyRequest request) {
        Company company = getCompanyFromDB();
        updateTool.updateEntity(company, request);
        companyRepository.save(company);
        return companyMapper.toResponse(company);
    }

    private Company getCompanyFromDB() {
        return companyRepository.findFirstByIdIsNotNull().orElseThrow(() -> new ResourceNotFoundException(1L));
    }

}
