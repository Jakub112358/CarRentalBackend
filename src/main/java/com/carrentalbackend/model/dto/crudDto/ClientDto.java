package com.carrentalbackend.model.dto.crudDto;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto implements CrudDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;

}
