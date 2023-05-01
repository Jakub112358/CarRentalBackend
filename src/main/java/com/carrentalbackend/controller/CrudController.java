package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.CrudDto;
import com.carrentalbackend.model.entity.CrudEntity;
import com.carrentalbackend.service.CrudService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor

public abstract class CrudController<T extends CrudEntity, K extends CrudDto> {
    protected final CrudService<T, K> service;

    @PostMapping
    public ResponseEntity<CrudDto> save(@RequestBody K dto) {
        CrudDto responseDTO = service.save(dto);
        URI newResourceLocation = getNewResourceLocation(responseDTO.getId());
        return ResponseEntity.created(newResourceLocation).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<K>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<K> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody K requestDto) {
        service.update(id, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    protected URI getNewResourceLocation(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
