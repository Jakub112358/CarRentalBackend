package com.carrentalbackend.features.generics;

import com.carrentalbackend.model.entity.CrudEntity;

public interface UpdateTool <T extends CrudEntity, U extends UpdateRequest>{
    void updateEntity(T entity, U updateRequest);
}
