package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.mapper.CompanyMapper;
import com.carrentalbackend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompanyService extends CrudService<Company, CompanyDto> {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        super(companyRepository, companyMapper);
        this.companyRepository = companyRepository;
    }

    //TODO use proper exception
    @Override
    public void update(Long id, CompanyDto requestDto) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (requestDto.getName() != null)
            company.setName(requestDto.getName());
        if (requestDto.getDomain() != null)
            company.setDomain(requestDto.getDomain());
        if (requestDto.getLogotype() != null)
            company.setDomain(requestDto.getDomain());
        if (requestDto.getAddress() != null)
            company.setAddress(requestDto.getAddress());
    }


    //TODO use proper exception
    @Override
    public void deleteById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException());
        nullBranchOffices(company);
        companyRepository.deleteById(id);
    }

    private void nullBranchOffices(Company company) {
        company.getBranchOffices().forEach((bo) -> bo.setCompany(null));
    }

}
