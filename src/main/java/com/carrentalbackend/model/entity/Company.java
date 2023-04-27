package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.dto.CrudDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String domain;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logotype;
    @OneToOne (cascade = CascadeType.ALL)
    private Address address;
    @OneToMany (mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<BranchOffice> branchOffices;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Finances finances;

    public static Company toEntity (CompanyDto responseDTO){
        return Company.builder()
                .name(responseDTO.getName())
                .domain(responseDTO.getDomain())
                .logotype(responseDTO.getLogotype())
                .address(responseDTO.getAddress())
                .build();
    }

    @Override
    public CrudDto toDto() {
        return CompanyDto.builder()
                .id(this.id)
                .name(this.name)
                .domain(this.domain)
                .logotype(this.logotype)
                .address(this.address)
                .build();
    }
}
