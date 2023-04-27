package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.dto.CrudDto;

public interface CrudEntity <T extends CrudDto> {
    T toDto();
}
