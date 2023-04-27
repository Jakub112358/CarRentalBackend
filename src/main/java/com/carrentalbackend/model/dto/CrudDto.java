package com.carrentalbackend.model.dto;

import com.carrentalbackend.model.entity.CrudEntity;

public interface CrudDto<T extends CrudEntity> {
    long getId();
    T toEntity();


}
