package com.carrentalbackend.features.offices;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.features.offices.rest.OfficeMapper;
import com.carrentalbackend.features.offices.rest.OfficeRequest;
import com.carrentalbackend.features.offices.rest.OfficeUpdateTool;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService extends CrudService<Office, OfficeRequest, OfficeRequest> {
    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final PickUpRepository pickUpRepository;
    private final CarReturnRepository carReturnRepository;

    public OfficeService(OfficeRepository repository,
                         OfficeMapper officeMapper,
                         OfficeUpdateTool updateTool,
                         EmployeeRepository employeeRepository,
                         CarRepository carRepository,
                         ReservationRepository reservationRepository,
                         PickUpRepository pickUpRepository,
                         CarReturnRepository carReturnRepository) {
        super(repository, officeMapper, updateTool);
        this.officeRepository = repository;
        this.employeeRepository = employeeRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.pickUpRepository = pickUpRepository;
        this.carReturnRepository = carReturnRepository;
    }


    @Override
    public void deleteById(Long id) {
        if (!officeRepository.existsById(id))
            throw new ResourceNotFoundException(id);

        nullOfficeInEmployees(id);
        nullOfficeInCars(id);
        nullPickUpOfficeInReservations(id);
        nullReturnOfficeInReservations(id);
        nullOfficeInPickUps(id);
        nullOfficeInCarReturns(id);
        officeRepository.deleteById(id);
    }

    private void nullOfficeInCarReturns(Long id) {
        List<CarReturn> carReturns = carReturnRepository.findAllByOffice_Id(id);
        carReturns.forEach(cr -> cr.setOffice(null));

    }

    private void nullOfficeInPickUps(Long id) {
        List<PickUp> pickUps = pickUpRepository.findAllByOffice_Id(id);
        pickUps.forEach(pu -> pu.setOffice(null));
    }

    private void nullReturnOfficeInReservations(Long id) {
        List<Reservation> reservations = reservationRepository.findAllByReturnOffice_id(id);
        reservations.forEach(r -> r.setReturnOffice(null));
    }

    private void nullPickUpOfficeInReservations(Long id) {
        List<Reservation> reservations = reservationRepository.findAllByPickUpOffice_id(id);
        reservations.forEach(r -> r.setPickUpOffice(null));
    }

    private void nullOfficeInCars(Long id) {
        List<Car> cars = carRepository.findAllByCurrentOffice_Id(id);
        cars.forEach(c -> c.setCurrentOffice(null));
    }

    private void nullOfficeInEmployees(Long id) {
        List<Employee> employees = employeeRepository.findAllByOffice_Id(id);
        employees.forEach(e -> e.setOffice(null));
    }

}
