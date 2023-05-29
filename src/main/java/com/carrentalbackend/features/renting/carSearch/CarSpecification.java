package com.carrentalbackend.features.renting.carSearch;


import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.enumeration.CarStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class CarSpecification implements Specification<Car> {
    private final CarSearchByCriteriaRequest criteria;

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                statusIsNotUnavailable(root, criteriaBuilder),
                makeIsMember(root, criteriaBuilder),
                modelIsMember(root, criteriaBuilder),
                mileageIsSmallerThanOrEqualTo(root, criteriaBuilder),
                yearGraterThanOrEqualTo(root, criteriaBuilder),
                bodyTypeIsMember(root, criteriaBuilder),
                colorIsMember(root, criteriaBuilder)
        );
    }

    private Predicate colorIsMember(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return nonNull(criteria.getColorOf()) ?
                criteriaBuilder.or(getColorsPredicatesArray(root, criteriaBuilder)) :
                alwaysTruePredicate(criteriaBuilder);
    }

    private Predicate[] getColorsPredicatesArray(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return criteria.getColorOf().stream()
                .map(c -> criteriaBuilder.equal(root.get("color"),c))
                .toArray(Predicate[]::new);
    }

    private Predicate bodyTypeIsMember(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return nonNull(criteria.getBodyTypeOf()) ?
                criteriaBuilder.or(getBodyTypesPredicatesArray(root, criteriaBuilder)) :
                alwaysTruePredicate(criteriaBuilder);
    }

    private Predicate[] getBodyTypesPredicatesArray(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return criteria.getBodyTypeOf().stream()
                .map(bt -> criteriaBuilder.equal(root.get("bodyType"), bt))
                .toArray(Predicate[]::new);
    }

    private Predicate makeIsMember(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return nonNull(criteria.getMakeOf()) ?
                criteriaBuilder.or(getMakePredicatesArray(root, criteriaBuilder)) :
                alwaysTruePredicate(criteriaBuilder);
    }

    private Predicate[] getMakePredicatesArray(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return criteria.getMakeOf().stream()
                .map(make -> criteriaBuilder.like(root.get("make"),make))
                .toArray(Predicate[]::new);
    }

    private Predicate modelIsMember(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return nonNull(criteria.getModelOf()) ?
                criteriaBuilder.or(getModelPredicatesArray(root, criteriaBuilder)) :
                alwaysTruePredicate(criteriaBuilder);
    }

    private Predicate[] getModelPredicatesArray(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return criteria.getModelOf().stream()
                .map(model -> criteriaBuilder.like(root.get("model"),model))
                .toArray(Predicate[]::new);
    }

    private Predicate mileageIsSmallerThanOrEqualTo(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return nonNull(criteria.getMaxMileage()) ?
                criteriaBuilder.lessThanOrEqualTo(root.get("mileage"), criteria.getMaxMileage()) :
                alwaysTruePredicate(criteriaBuilder);
    }

    private Predicate yearGraterThanOrEqualTo(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return nonNull(criteria.getMinYearOfManufacture()) ?
                criteriaBuilder.greaterThanOrEqualTo(root.get("yearOfManufacture"), criteria.getMinYearOfManufacture()) :
                alwaysTruePredicate(criteriaBuilder);
    }

    private Predicate statusIsNotUnavailable(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.notEqual(root.get("status"), CarStatus.UNAVAILABLE);
    }

    private Predicate alwaysTruePredicate(CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

}
