package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.CrudDto;
import com.carrentalbackend.model.entity.CrudEntity;

public interface CrudMapper <T extends CrudEntity, K extends CrudDto> {
    T toEntity(K dto);
    K toDto(T entity);
}
