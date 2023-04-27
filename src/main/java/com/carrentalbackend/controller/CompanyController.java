package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carrentalbackend.controller.ApiConstraints.COMPANY;

@RestController
@RequestMapping(COMPANY)
public class CompanyController extends CrudController<CompanyDto> {
    private final CompanyService companyService;

    public CompanyController(CompanyService service) {
        super(service);
        this.companyService = service;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> findCompanies() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findCompanyById(@PathVariable long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCompany(@RequestBody CompanyDto updatedCompany, @PathVariable long id) {
        companyService.update(id, updatedCompany);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
