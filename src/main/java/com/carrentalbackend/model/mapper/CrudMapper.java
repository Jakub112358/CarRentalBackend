package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.crudDto.CrudDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.CrudEntity;

public interface CrudMapper <T extends CrudEntity, K extends CrudDto> {
    T toNewEntity(K dto);
    UpdateDto toUpdateEntity(K dto);
    K toDto(T entity);

}
