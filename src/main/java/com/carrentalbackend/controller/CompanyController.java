package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.carrentalbackend.controller.ApiConstraints.COMPANY;

@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;

    @PostMapping
    public ResponseEntity<CompanyDto> saveCompany(@RequestBody CompanyDto company) {
        CompanyDto responseDTO = service.save(company);
        URI newResourceLocation = getNewResourceLocation(responseDTO.getId());
        return ResponseEntity.created(newResourceLocation).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> findCompanies(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findCompanyById(@PathVariable long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCompany (@RequestBody CompanyDto updatedCompany, @PathVariable long id){
        service.update(id, updatedCompany);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private URI getNewResourceLocation(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
