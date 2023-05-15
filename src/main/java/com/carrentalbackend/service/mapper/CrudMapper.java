package com.carrentalbackend.service.mapper;

import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.CrudEntity;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.Response;

public interface CrudMapper<T extends CrudEntity, U extends UpdateRequest, V extends CreateRequest> {

    T toNewEntity(V request);

    UpdateDto toUpdateDto(U request);

    Response toResponse(T entity);


}
