package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.CrudDto;
import com.carrentalbackend.model.entity.CrudEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public abstract class CrudService<T extends CrudEntity> {
    protected final JpaRepository<T, Long> repository;
    public CrudDto<T> save (CrudDto<T> requestDto){
        T entity = requestDto.toEntity();
        repository.save(entity);
        return entity.toDto();
    }
}
