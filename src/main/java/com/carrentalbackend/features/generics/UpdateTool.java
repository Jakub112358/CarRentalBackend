package com.carrentalbackend.features.generics;

import com.carrentalbackend.model.entity.CrudEntity;

public interface UpdateTool <T extends CrudEntity, U extends Request>{
    void updateEntity(T entity, U updateRequest);
}
