package com.carrentalbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.OFFICE;

@RestController
@RequestMapping(OFFICE)
@RequiredArgsConstructor
public class OfficeController {
}
