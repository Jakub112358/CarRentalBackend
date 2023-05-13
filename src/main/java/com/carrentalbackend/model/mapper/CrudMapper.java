package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.CrudEntity;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.Response;

public interface CrudMapper<T extends CrudEntity> {

    T toNewEntity(CreateRequest request);

    UpdateDto toUpdateDto(UpdateRequest request);

    Response toResponse(T entity);


}
