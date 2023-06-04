package com.carrentalbackend.features.offices.rest;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.model.entity.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeMapper implements CrudMapper<Office, OfficeRequest> {

    @Override
    public Office toNewEntity(OfficeRequest request) {

        return Office.builder()
                .address(request.getAddress())
                .build();
    }

    @Override
    public OfficeResponse toResponse(Office entity) {
        return OfficeResponse.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .build();
    }

}
