package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyDto save(CompanyDto companyDTO) {
        Company companyEntity = Company.toEntity(companyDTO);
        companyRepository.save(companyEntity);
        return CompanyDto.toDTO(companyEntity);
    }

    public List<CompanyDto> findAll() {
        return companyRepository.findAll().stream().map(CompanyDto::toDTO).toList();
    }

    //TODO create proper exception
    public CompanyDto findById(long id) {
        return CompanyDto.toDTO(companyRepository.findById(id).orElseThrow(() -> new RuntimeException()));
    }

    //TODO use proper exception
    public void update(long id, CompanyDto update) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (update.getName() != null)
            company.setName(update.getName());
        if (update.getDomain() != null)
            company.setDomain(update.getDomain());
        if (update.getLogotype() != null)
            company.setDomain(update.getDomain());
        if (update.getAddress() != null)
            company.setAddress(update.getAddress());
    }

    //TODO use proper exception
    public void deleteById(long id) {
        if (companyRepository.existsById(id)) {
            deleteCompany(id);
        } else {
            throw new RuntimeException();
        }
    }
    //TODO user proper exception

    private void deleteCompany(long id){
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException());
        nullBranchOffices(company);
        companyRepository.deleteById(id);
    }

    private void nullBranchOffices(Company company){
        System.out.println("nulling branch offices");
        company.getBranchOffices().forEach((bo) -> bo.setCompany(null));
    }

}
