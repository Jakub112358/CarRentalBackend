package com.carrentalbackend.features.renting.reservations;

import com.carrentalbackend.exception.ForbiddenOperationException;
import com.carrentalbackend.exception.ForbiddenResourceException;
import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.RentingUtil;
import com.carrentalbackend.features.renting.RentingValidation;
import com.carrentalbackend.features.renting.reservations.rest.*;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.entity.Finances;
import com.carrentalbackend.model.entity.Income;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.FinancesRepository;
import com.carrentalbackend.repository.IncomeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService extends CrudService<Reservation, ReservationCreateRequest, ReservationUpdateRequest> {
    private final IncomeRepository incomeRepository;
    private final ReservationRepository reservationRepository;
    private final FinancesRepository financesRepository;
    private final ReservationMapper reservationMapper;
    private final RentingValidation rentingValidation;
    private final RentingUtil rentingUtil;
    private final ClientRepository clientRepository;

    public ReservationService(ReservationRepository repository,
                              ReservationMapper mapper,
                              IncomeRepository incomeRepository,
                              ReservationRepository reservationRepository,
                              FinancesRepository financesRepository,
                              ReservationUpdateTool updateTool,
                              RentingValidation rentingValidation,
                              RentingUtil rentingUtil,
                              ClientRepository clientRepository) {
        super(repository, mapper, updateTool);
        this.incomeRepository = incomeRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = mapper;
        this.financesRepository = financesRepository;
        this.rentingValidation = rentingValidation;
        this.rentingUtil = rentingUtil;
        this.clientRepository = clientRepository;
    }


    @Override
    public Response save(ReservationCreateRequest request) {
        validateRequest(request);
        Response response = super.save(request);
        addIncome(response);
        return response;
    }


    public Set<ReservationResponse> findByClientId(Long clientId) {
        return reservationRepository
                .findAllByClient_Id(clientId)
                .stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toSet());
    }

    public void throwIfNotPermittedToUpdate(Long reservationId, ReservationUpdateRequest updateRequest, Authentication auth) {
        if (authHasRole(auth, "ROLE_ADMIN") || authHasRole(auth, "ROLE_EMPLOYEE"))
            return;
        if (!updateRequest.getStatus().equals(ReservationStatus.CANCELLED))
            throw new ForbiddenOperationException("Client is allowed only to cancel his reservations");

        Long clientId = extractClientId(reservationId);
        throwIfIdDoesNotMatchUser(clientId, auth.getName());
    }

    @Override
    public void deleteById(Long id) {
        if(!reservationRepository.existsById(id))
            throw new ResourceNotFoundException(id);

        nullReservationInIncomes(id);
        reservationRepository.deleteById(id);
    }

    private void nullReservationInIncomes(Long id) {
        incomeRepository.findAllByReservation_Id(id).forEach(i -> i.setReservation(null));
    }


    private void addIncome(Response response) {
        Income income = reservationMapper.reservationResponseToIncome(response);
        incomeRepository.save(income);
    }

    void throwIfNotPermittedByReservationId(Long reservationId, Authentication auth) {
        //user with role CLIENT is restricted only to read/update his own data
        Long clientId = extractClientId(reservationId);
        throwIfNotPermittedByClientId(clientId, auth);
    }

    void throwIfNotPermittedByClientId(Long clientId, Authentication auth) {
        //user with role CLIENT is restricted only to read/update his own data
        if (authHasRole(auth, "ROLE_CLIENT"))
            throwIfIdDoesNotMatchUser(clientId, auth.getName());
    }

    void throwIfNotAdminOrEmployee(Authentication auth) {
        if (!(authHasRole(auth, "ROLE_ADMIN") || authHasRole(auth, "ROLE_EMPLOYEE")))
            throw new ForbiddenResourceException("client", 0L);
    }

    private Long extractClientId(Long reservationId) {
        var reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException(reservationId));
        return reservation.getClient().getId();
    }

    private void throwIfIdDoesNotMatchUser(Long clientId, String name) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(clientId));
        if (!client.getEmail().equals(name))
            throw new ForbiddenResourceException("client", clientId);
    }

    private boolean authHasRole(Authentication auth, String role) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    private void validateRequest(ReservationCreateRequest request) {
        rentingValidation.throwIfInvalidRentingDatesOrder(request.getDateFrom(), request.getDateTo());

        var rentalLength = rentingUtil.calculateRentalLength(request.getDateFrom(), request.getDateTo());
        var sameOffices = request.getPickUpOfficeId().equals(request.getReturnOfficeId());
        var expectedPrice = rentingUtil.calculatePrice(request.getCarId(), rentalLength, sameOffices);
        rentingValidation.throwIfInvalidPrice(expectedPrice, request);
    }

    public void performCashback(Long reservationId) {
        var reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException(reservationId));
        var amount = rentingUtil.calculateCashback(reservation);


        Finances finances = financesRepository.findFirstByIdIsNotNull().orElseThrow(() -> new ResourceNotFoundException(1L));
        var refund = Income.builder()
                .incomeValue(amount)
                .reservation(reservation)
                .finances(finances)
                .build();
        incomeRepository.save(refund);
    }

    public void performCashbackIfClient(Authentication auth, Long reservationId) {
        if (authHasRole(auth, "ROLE_CLIENT"))
            performCashback(reservationId);
    }
}
