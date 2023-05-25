package com.carrentalbackend.features.offices;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.features.offices.rest.OfficeMapper;
import com.carrentalbackend.features.offices.rest.OfficeRequest;
import com.carrentalbackend.features.offices.rest.OfficeUpdateTool;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService extends CrudService<Office, OfficeRequest> {
    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public OfficeService(OfficeRepository repository,
                         OfficeMapper officeMapper,
                         OfficeUpdateTool updateTool,
                         EmployeeRepository employeeRepository,
                         CarRepository carRepository,
                         ReservationRepository reservationRepository) {
        super(repository, officeMapper, updateTool);
        this.officeRepository = repository;
        this.employeeRepository = employeeRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }


    @Override
    public void deleteById(Long id) {
        if(!officeRepository.existsById(id))
            throw new ResourceNotFoundException(id);

        nullOfficeInEmployees(id);
        nullOfficeInCars(id);
        nullPickUpOfficeInReservations(id);
        nullReturnOfficeInReservations(id);
        officeRepository.deleteById(id);
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
