package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.CrudDto;
import com.carrentalbackend.model.entity.CrudEntity;
import com.carrentalbackend.model.mapper.CrudMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public abstract class CrudService<T extends CrudEntity, K extends CrudDto> {
    protected final JpaRepository<T, Long> repository;
    protected final CrudMapper <T, K> mapper;

    public K save (K requestDto){
        T entity = mapper.toEntity(requestDto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public List<K> findAll(){
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    //TODO use proper exception
    public K findById(Long id){
        return mapper.toDto(repository.findById(id).orElseThrow(()->new ResourceNotFoundException(id)));
    }


    public abstract void update(Long id, K requestDto);
    public abstract void deleteById(Long id);
}
