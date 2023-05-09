package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.crudDto.ReservationDto;
import com.carrentalbackend.model.entity.Finances;
import com.carrentalbackend.model.entity.Income;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.mapper.ReservationMapper;
import com.carrentalbackend.model.rest.ReservationClientResponse;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.repository.IncomeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import com.carrentalbackend.service.validator.ReservationValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService extends CrudService<Reservation, ReservationDto> {
    private final ReservationValidator reservationValidator;
    private final IncomeRepository incomeRepository;
    private final ReservationRepository reservationRepository;
    private final CompanyRepository companyRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository repository,
                              ReservationMapper mapper,
                              ReservationValidator reservationValidator,
                              IncomeRepository incomeRepository,
                              ReservationRepository reservationRepository,
                              CompanyRepository companyRepository) {
        super(repository, mapper);
        this.reservationValidator = reservationValidator;
        this.incomeRepository = incomeRepository;
        this.reservationRepository = reservationRepository;
        this.companyRepository = companyRepository;
        this.reservationMapper = mapper;
    }

    @Override
    public ReservationDto save(ReservationDto requestDto) {
        reservationValidator.validate(requestDto);
        ReservationDto response = super.save(requestDto);
        addIncome(response);
        return response;
    }

    private void addIncome(ReservationDto dto) {
        Income income = reservationToIncome(dto);
        incomeRepository.save(income);
    }

    private Income reservationToIncome(ReservationDto dto) {
        //TODO company id hardcoded?
        Reservation reservation = reservationRepository.getReferenceById(dto.getId());
        Finances finances = companyRepository.findById(1L).orElseThrow().getFinances();
        return Income.builder()
                .incomeValue(dto.getPrice())
                .reservation(reservation)
                .finances(finances)
                .build();

    }

    @Override
    public void deleteById(Long id) {
    }

    public List<ReservationClientResponse> findByClientId(Long clientId) {
        return reservationRepository
                .findAllByClient_Id(clientId)
                .stream()
                .map(reservationMapper::toReservationClientResponse)
                .toList();
    }
}
