package com.carrentalbackend.features.generics;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RequiredArgsConstructor

public abstract class CrudController<T extends CreateRequest, U extends UpdateRequest> {
    //TODO: refactor service!
    protected final CrudService<?, U, T> service;

    @PostMapping
    public ResponseEntity<Response> save(@Valid @RequestBody T createRequest) {
        Response response = service.save(createRequest);
        URI newResourceLocation = getNewResourceLocation(response.getId());
        return ResponseEntity.created(newResourceLocation).body(response);
    }

    @GetMapping
    public ResponseEntity<Set<Response>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody U updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
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
