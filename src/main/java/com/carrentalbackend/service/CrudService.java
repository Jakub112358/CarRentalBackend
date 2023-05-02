package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.CrudDto;
import com.carrentalbackend.model.entity.CrudEntity;
import com.carrentalbackend.model.mapper.CrudMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Transactional
public abstract class CrudService<T extends CrudEntity, K extends CrudDto> {
    protected final JpaRepository<T, Long> repository;
    protected final CrudMapper<T, K> mapper;

    public K save(K requestDto) {
        T entity = mapper.toNewEntity(requestDto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public List<K> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    //TODO use proper exception
    public K findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public K update(Long id, K requestDto) {
        T updateEntity = mapper.toUpdateEntity(requestDto);
        T instance = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        //extracting fields of sent request using reflection
        //Field[] fields = requestDto.getClass().getDeclaredFields();
        Field[] fields = updateEntity.getClass().getDeclaredFields();
        //extracting non-null field names from request and then updating corresponding fields in instance
        Arrays.stream(fields)
                .filter(f -> !f.getName().equals("id"))
                .peek(f -> f.setAccessible(true))
                .filter(f -> checkIfNotNull(f, updateEntity))
                .map(Field::getName)
                .forEach(name -> updateField(name, instance, updateEntity));

        return mapper.toDto(instance);
    }


    public abstract void deleteById(Long id);

    private void updateField(String name, T instance, T updateEntity) {
        try {
            //extracting field with new data from request
            Field requestField = updateEntity.getClass().getDeclaredField(name);
            requestField.setAccessible(true);
            //declaring an object transferring new data
            Object value = requestField.get(updateEntity);
            //extracting field to update
            Field instanceField = instance.getClass().getDeclaredField(name);
            instanceField.setAccessible(true);
            //setting new value
            instanceField.set(instance, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean checkIfNotNull(Field f, T updateEntity) {
        try {
            return f.get(updateEntity) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
