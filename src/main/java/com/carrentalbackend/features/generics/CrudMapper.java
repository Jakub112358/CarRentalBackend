package com.carrentalbackend.features.generics;

import com.carrentalbackend.model.entity.CrudEntity;

public interface CrudMapper<T extends CrudEntity, U extends UpdateRequest, V extends CreateRequest> {

    T toNewEntity(V request);

    UpdateDto toUpdateDto(U request);

    Response toResponse(T entity);


}
