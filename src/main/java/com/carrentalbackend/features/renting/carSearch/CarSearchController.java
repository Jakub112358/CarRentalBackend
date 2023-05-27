package com.carrentalbackend.features.renting.carSearch;

import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.CAR_SEARCH;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@CrossOrigin(origins = ORIGIN)
@RequestMapping(CAR_SEARCH)
@RequiredArgsConstructor
public class CarSearchController {
    private final CarSearchService service;

    @PostMapping("/search")
    public ResponseEntity<Set<CarSearchResponse>> findByAvailableInDatesAndCriteria(@RequestBody(required = false) CarSearchByCriteriaRequest criteria,
                                                                                    @RequestParam @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                                                    @RequestParam @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                                                    @RequestParam @NotNull @ExistingOfficeId Long pickUpOfficeId,
                                                                                    @RequestParam @NotNull @ExistingOfficeId Long returnOfficeId) {
        Set<Car> cars = service.findByCriteria(criteria);

        return ResponseEntity.ok(service.findByAvailableInTerm(dateFrom, dateTo, pickUpOfficeId, returnOfficeId, cars));

    }

}
