package com.carrentalbackend.features.renting.carSearch;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.util.factories.CarFactory;
import com.carrentalbackend.util.factories.CompanyFactory;
import com.carrentalbackend.util.factories.PriceListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CarSearchServiceIT extends BaseIT {

    @BeforeEach
    void setUp() {
        dbOperations.cleanCarsTable();
        dbOperations.cleanReservationTable();
        if (companyRepository.findFirstByIdIsNotNull().isEmpty())
            dbOperations.addSimpleCompanyToDB();
    }

    @Test
    public void whenFindNotUnavailable_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setStatus(CarStatus.AVAILABLE);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setStatus(CarStatus.UNAVAILABLE);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setStatus(CarStatus.RENTED);

        //when
        var result = carSearchService.findNotUnavailable();

        //then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(car1, car3)));
        assertFalse(result.contains(car2));
    }

    @Test
    public void whenFindByCriteria_WithUnavailableStatus_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setStatus(CarStatus.AVAILABLE);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setStatus(CarStatus.UNAVAILABLE);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setStatus(CarStatus.RENTED);

        //and
        var request = CarSearchByCriteriaRequest.builder()
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(car1, car3)));
        assertFalse(result.contains(car2));
    }

    @Test
    public void whenFindByCriteria_WithMakeIsMember_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var make1 = "mercedes";
        var make2 = "bmw";
        var make3 = "ferrari";

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setMake(make3);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setMake(make2);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setMake(make3);
        var car4 = dbOperations.addSimpleCarToDb(office, priceList);
        car4.setMake(make1);

        //and
        var request = CarSearchByCriteriaRequest.builder()
                .makeOf(Set.of(make3))
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(car1, car3)));
        assertFalse(result.contains(car2));
        assertFalse(result.contains(car4));
    }

    @Test
    public void whenFindByCriteria_WithModelIsMember_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var model1 = "a";
        var model2 = "b";
        var model3 = "c";
        var model4 = "d";

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setModel(model1);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setModel(model2);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setModel(model3);
        var car4 = dbOperations.addSimpleCarToDb(office, priceList);
        car4.setModel(model4);

        //and
        var request = CarSearchByCriteriaRequest.builder()
                .modelOf(Set.of(model1, model2, model3))
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(3, result.size());
        assertTrue(result.containsAll(List.of(car1, car2, car3)));
        assertFalse(result.contains(car4));
    }

    @Test
    public void whenFindByCriteria_WithMaxMileage_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setMileage(4999);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setMileage(5000);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setMileage(5001);
        var car4 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setMileage(Integer.MAX_VALUE);

        //and
        var request = CarSearchByCriteriaRequest.builder()
                .maxMileage(5000)
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(car1, car2)));
        assertFalse(result.contains(car3));
        assertFalse(result.contains(car4));
    }

    @Test
    public void whenFindByCriteria_WithYearOfManufactureFrom_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setYearOfManufacture(2000);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setYearOfManufacture(2010);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setYearOfManufacture(2020);

        //and
        var request = CarSearchByCriteriaRequest.builder()
                .minYearOfManufacture(2010)
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(car2, car3)));
        assertFalse(result.contains(car1));
    }

    @Test
    public void whenFindByCriteria_WithBodyTypeIsMember_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();


        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setBodyType(CarBodyType.SUV);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setBodyType(CarBodyType.CITY_CAR);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setBodyType(CarBodyType.ESTATE);
        var car4 = dbOperations.addSimpleCarToDb(office, priceList);
        car4.setBodyType(CarBodyType.SUV);

        //and
        var request = CarSearchByCriteriaRequest.builder()
                .bodyTypeOf(Set.of(CarBodyType.SUV, CarBodyType.ESTATE))
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(3, result.size());
        assertTrue(result.containsAll(List.of(car1, car3, car4)));
        assertFalse(result.contains(car2));
    }

    @Test
    public void whenFindByCriteria_WithColorIsMember_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();


        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setColor(Color.BLUE);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setColor(Color.BLACK);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setColor(Color.RED);


        //and
        var request = CarSearchByCriteriaRequest.builder()
                .colorOf(Set.of(Color.RED))
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(car3));
        assertFalse(result.contains(car1));
        assertFalse(result.contains(car2));
    }

    @Test
    public void whenFindByCriteria_WithMultipleCriteria_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();


        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setColor(Color.BLUE);
        car1.setBodyType(CarBodyType.CITY_CAR);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setColor(Color.RED);
        car2.setBodyType(CarBodyType.SUV);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setColor(Color.WHITE);
        car3.setBodyType(CarBodyType.SUV);

        //and
        var request = CarSearchByCriteriaRequest.builder()
                .colorOf(Set.of(Color.BLUE, Color.WHITE))
                .bodyTypeOf(Set.of(CarBodyType.SUV, CarBodyType.ESTATE))
                .build();

        //when
        var result = carSearchService.findByCriteria(request);

        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(car3));
        assertFalse(result.contains(car1));
        assertFalse(result.contains(car2));
    }

    @Test
    public void whenFindByAvailableInTerm_WithSameOffices_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        var cars = List.of(car1, car2);

        //and
        var date = LocalDate.now();

        //when
        var result = carSearchService.findByAvailableInTerm(date, date, office.getId(), office.getId(), cars);

        //then
        assertEquals(2, result.size());

        //and
        var carSearchResponse = result.stream().findAny().orElseThrow();
        assertEquals(BigDecimal.valueOf(PriceListFactory.simplePriceListShortTermPrice), carSearchResponse.getPrice());
        assertEquals(CarFactory.simpleCarMileage, carSearchResponse.getCarResponse().getMileage());
    }

    @Test
    public void whenFindByAvailableInTerm_WithDifferentOffices_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office1 = dbOperations.addSimpleOfficeToDB();
        var office2 = dbOperations.addSimpleOfficeToDB();
        var car1 = dbOperations.addSimpleCarToDb(office1, priceList);
        var cars = List.of(car1);

        //and
        var date = LocalDate.now();

        //when
        var result = carSearchService.findByAvailableInTerm(date, date, office1.getId(), office2.getId(), cars);

        //then
        assertEquals(1, result.size());

        //and
        var carSearchResponse = result.stream().findAny().orElseThrow();
        assertEquals(
                BigDecimal.valueOf(PriceListFactory.simplePriceListShortTermPrice + CompanyFactory.simpleCompanyDifferentOfficesExtraCharge),
                carSearchResponse.getPrice());
        assertEquals(CarFactory.simpleCarMileage, carSearchResponse.getCarResponse().getMileage());
    }

    @Test
    public void whenFindByAvailableInTerm_WithInterferingReservation_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setModel("A");
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setModel("B");
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);
        car3.setModel("C");
        var car4 = dbOperations.addSimpleCarToDb(office, priceList);
        car4.setModel("D");
        var car5 = dbOperations.addSimpleCarToDb(office, priceList);
        car5.setModel("E");
        var cars = List.of(car1, car2, car3, car4, car5);

        //and
        var date = LocalDate.now();
        var searchDateTo = date.plusDays(10);

        //and
        addReservation(car1, office, office, date.minusDays(100), date.plusDays(100));
        addReservation(car2, office, office, date.minusDays(100), date.plusDays(5));
        addReservation(car3, office, office, date.plusDays(5), date.plusDays(5));
        addReservation(car4, office, office, date.minusDays(5), date.plusDays(100));


        //when
        var result = carSearchService.findByAvailableInTerm(date, searchDateTo, office.getId(), office.getId(), cars);

        //then
        assertEquals(1, result.size());

        //and
        var carSearchResponse = result.stream().findAny().orElseThrow();
        assertEquals("E", carSearchResponse.getCarResponse().getModel());
    }

    @Test
    public void whenFindByAvailableInTerm_thenIgnoreRealizedReservation() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setModel("A");
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setModel("B");

        var cars = List.of(car1, car2);

        //and
        var date = LocalDate.now();
        var searchDateTo = date.plusDays(10);

        //and
        var reservation1 = addReservation(car1, office, office, date.minusDays(100), date.plusDays(100));
        reservation1.setStatus(ReservationStatus.PLANNED);
        var reservation2 = addReservation(car2, office, office, date.minusDays(100), date.plusDays(100));
        reservation2.setStatus(ReservationStatus.REALIZED);

        //when
        var result = carSearchService.findByAvailableInTerm(date, searchDateTo, office.getId(), office.getId(), cars);

        //then
        assertEquals(1, result.size());

        //and
        var carSearchResponse = result.stream().findAny().orElseThrow();
        assertEquals("B", carSearchResponse.getCarResponse().getModel());
    }

    @Test
    public void whenFindByAvailableInTerm_withDifferentCurrentOffices_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office1 = dbOperations.addSimpleOfficeToDB();
        var office2 = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office1, priceList);
        var car2 = dbOperations.addSimpleCarToDb(office2, priceList);
        var cars = List.of(car1, car2);

        //and
        var searchDayFrom = LocalDate.now().plusDays(10);
        var searchDateTo = LocalDate.now().plusDays(20);

        //when
        var result = carSearchService.findByAvailableInTerm(searchDayFrom, searchDateTo, office1.getId(), office1.getId(), cars);

        //then
        assertEquals(1, result.size());
    }

    @Test
    public void whenFindByAvailableInTerm_withDifferentCurrentOfficesAndPlannedReservationFinishedInSearchOffice_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office1 = dbOperations.addSimpleOfficeToDB();
        var office2 = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office1, priceList);
        var car2 = dbOperations.addSimpleCarToDb(office2, priceList);
        var cars = List.of(car1, car2);

        //and
        var searchDayFrom = LocalDate.now().plusDays(10);
        var searchDateTo = LocalDate.now().plusDays(20);

        //and
        addReservation(car2, office2, office1, LocalDate.now(), LocalDate.now().plusDays(3));

        //when
        var result = carSearchService.findByAvailableInTerm(searchDayFrom, searchDateTo, office1.getId(), office1.getId(), cars);

        //then
        assertEquals(2, result.size());
    }

    @Test
    public void whenFindByAvailableInTerm_withSameCurrentOfficesAndPlannedReservationFinishedInDifferentOffice_thenCorrectAnswer() {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office1 = dbOperations.addSimpleOfficeToDB();
        var office2 = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office1, priceList);
        var car2 = dbOperations.addSimpleCarToDb(office1, priceList);
        var cars = List.of(car1, car2);

        //and
        var searchDayFrom = LocalDate.now().plusDays(10);
        var searchDateTo = LocalDate.now().plusDays(20);

        //and
        addReservation(car1, office1, office2, LocalDate.now(), LocalDate.now().plusDays(3));

        //when
        var result = carSearchService.findByAvailableInTerm(searchDayFrom, searchDateTo, office1.getId(), office1.getId(), cars);

        //then
        assertEquals(1, result.size());
    }

    private Reservation addReservation(Car car, Office pickUpoffice, Office returnOffice, LocalDate startDate, LocalDate endDate) {
        var reservation = dbOperations.addSimpleReservationToDB(car, pickUpoffice, returnOffice);
        reservation.setDateFrom(startDate);
        reservation.setDateTo(endDate);
        return reservation;
    }

}
