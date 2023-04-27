package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.dto.CrudDto;
import com.carrentalbackend.model.dto.OfficeDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchOffice implements CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "branchOffice", cascade = CascadeType.PERSIST)
    private List<Employee> employees;
    @OneToMany (mappedBy = "currentBranchOffice", cascade = CascadeType.PERSIST)
    private List<Car> availableCars;
    @ManyToOne
    private Company company;
    @OneToMany (mappedBy = "pickUpOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> pickUpReservations;
    @OneToMany (mappedBy = "returnOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> returnReservations;

    @Override
    public OfficeDto toDto() {
        return OfficeDto.builder()
                .id(this.id)
                .address(this.address)
                .build();
    }
}
