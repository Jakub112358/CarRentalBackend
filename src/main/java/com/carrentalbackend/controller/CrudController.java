package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.CrudDto;
import com.carrentalbackend.service.CrudService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor

public abstract class CrudController<T extends CrudDto> {
    protected final CrudService<?> service;
    @PostMapping
    public ResponseEntity<CrudDto> save(@RequestBody T dto) {
        CrudDto responseDTO = service.save(dto);
        URI newResourceLocation = getNewResourceLocation(responseDTO.getId());
        return ResponseEntity.created(newResourceLocation).body(responseDTO);
    }

    protected URI getNewResourceLocation(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
