package com.carrentalbackend.features.generics;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.entity.CrudEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public abstract class CrudService<T extends CrudEntity, U extends Request> {
    protected final JpaRepository<T, Long> repository;
    protected final CrudMapper<T, U> mapper;
    protected final UpdateTool<T, U> updateTool;

    public Response save(U request) {
        T entity = mapper.toNewEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    public Set<Response> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toSet());
    }

    public Response findById(Long id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public Response update(Long id, U request) {
        T instance = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        updateTool.updateEntity(instance, request);

        return mapper.toResponse(instance);
    }

    public abstract void deleteById(Long id);

}
