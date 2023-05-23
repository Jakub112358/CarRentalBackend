package com.carrentalbackend.features.companies.rest;

import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest implements Request, UpdateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String domain;
    private byte[] logotype;
    @Valid
    @NotNull
    private Address address;
    @Min(0)
    private double differentOfficesExtraCharge;
}
