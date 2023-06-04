package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee extends User implements CrudEntity {

@Builder
    public Employee(Long id, String email, String password, Role role, String firstName, String lastName, JobPosition jobPosition, Office office, List<PickUp> pickUps) {
        super(id, email, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = jobPosition;
        this.office = office;
        this.pickUps = pickUps;
    }

    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;
    @ManyToOne (fetch = FetchType.LAZY)
    private Office office;
    @OneToMany (mappedBy = "employee", cascade = CascadeType.PERSIST)
    private List<PickUp> pickUps;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Employee))
            return false;

        Employee other = (Employee) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
