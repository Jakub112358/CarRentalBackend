package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.companies.rest.CompanyRequest;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Company.CompanyBuilder;
import com.carrentalbackend.model.entity.Finances;

import static com.carrentalbackend.features.companies.rest.CompanyRequest.CompanyRequestBuilder;


public class CompanyFactory {
    public static final String simpleCompanyName = "company";
    public static final String simpleCompanyDomain = "www.company.com";
    public static final double simpleCompanyDifferentOfficesExtraCharge = 9.99;
    public static final byte[] simpleCompanyLogotype = new byte[1];

    public static CompanyBuilder getSimpleCompanyBuilder(){
        return Company.builder()
                .name(simpleCompanyName)
                .domain(simpleCompanyDomain)
                .differentOfficesExtraCharge(simpleCompanyDifferentOfficesExtraCharge)
                .logotype(simpleCompanyLogotype)
                .address(AddressFactory.getSimpleAddress())
                .finances(new Finances());
    }

    public static CompanyRequestBuilder getSimpleCompanyRequestBuilder(){
        return CompanyRequest.builder()
                .name(simpleCompanyName)
                .domain(simpleCompanyDomain)
                .differentOfficesExtraCharge(simpleCompanyDifferentOfficesExtraCharge)
                .logotype(simpleCompanyLogotype)
                .address(AddressFactory.getSimpleAddress());
    }
}
