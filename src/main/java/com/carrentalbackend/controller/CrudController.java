package com.carrentalbackend.controller;

import com.carrentalbackend.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class CrudController<T> {
    private final CompanyService service;


}
