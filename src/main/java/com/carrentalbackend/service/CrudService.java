package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.CrudEntity;
import com.carrentalbackend.model.mapper.CrudMapper;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public abstract class CrudService<T extends CrudEntity> {
    protected final JpaRepository<T, Long> repository;
    protected final CrudMapper<T> mapper;

    public Response save(CreateRequest request) {
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

    public Response update(Long id, UpdateRequest request) {
        T instance = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        //mapping requestDto to updateDto, which has exactly same field names as corresponding entity
        UpdateDto updateDto = mapper.toUpdateDto(request);
        //extracting fields of sent request using reflection
        Field[] fields = updateDto.getClass().getDeclaredFields();
        //extracting non-null field names from request and then updating corresponding fields in instance
        Arrays.stream(fields)
                .filter(f -> !f.getName().equals("id"))
                .peek(f -> f.setAccessible(true))
                .filter(f -> checkIfNotNull(f, updateDto))
                .map(Field::getName)
                .forEach(name -> updateField(name, instance, updateDto));

        return mapper.toResponse(instance);
    }

    public abstract void deleteById(Long id);

    private void updateField(String name, T instance, UpdateDto updateDto) {
        try {
            //extracting field with new data from request
            Field requestField = updateDto.getClass().getDeclaredField(name);
            requestField.setAccessible(true);
            //declaring an object transferring new data
            Object value = requestField.get(updateDto);
            //extracting field to update
            Field instanceField = instance.getClass().getDeclaredField(name);
            instanceField.setAccessible(true);
            //setting new value
            instanceField.set(instance, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean checkIfNotNull(Field f, UpdateDto updateDto) {
        try {
            return f.get(updateDto) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
