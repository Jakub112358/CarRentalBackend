package com.carrentalbackend.features.generics;

import com.carrentalbackend.model.entity.CrudEntity;

public interface CrudMapper<T extends CrudEntity, U extends CreateRequest> {

    T toNewEntity(U request);

    Response toResponse(T entity);


}
