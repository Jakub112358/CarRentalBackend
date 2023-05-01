package com.carrentalbackend.util.factories;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.entity.Address;

public class CompanyFactory {
    public static CompanyDto simpleCompanyDTO(){
        return CompanyDto.builder()
                .name("simple company name")
                .domain("www.simple.company")
                .address(new Address())
                .build();
    }
}
