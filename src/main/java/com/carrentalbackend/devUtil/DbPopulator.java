package com.carrentalbackend.devUtil;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.dto.OfficeDto;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.service.CompanyService;
import com.carrentalbackend.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
@RequiredArgsConstructor
@Transactional
public class DbPopulator {
    private final CompanyService companyService;
    private final OfficeService officeService;
    private Address[] addresses;
    private int addressCounter = 0;

    @PostConstruct
    public void populateDB() throws IOException {
        loadAddresses();
        addCompany();
        addBranchOffices();
    }

    private void addBranchOffices() {
        for (int i = 0; i < 3; i++) {
            officeService.save(OfficeDto.builder().address(getAddress()).companyId(1L).build());
        }
    }

    private void addCompany() throws IOException {
        companyService.save(CompanyDto.builder()
                .name("Company1")
                .domain("www.company1.com")
                .logotype(getPicture())
                .address(getAddress())
                .build());
    }

    private byte[] getPicture() throws IOException {
        File file = new File("src/main/java/com/carrentalbackend/devUtil/carIcon.png");
        return Files.readAllBytes(file.toPath());
    }

    private Address getAddress() {
        return this.addresses[addressCounter++];
    }

    private void loadAddresses() {
        this.addresses = new Address[]{
                new Address(0L, "11-111", "town1", "streetA", "1"),
                new Address(0L, "22-111", "town1", "streetB", "2"),
                new Address(0L, "33-111", "town1", "streetC", "3"),
                new Address(0L, "44-111", "town2", "streetA", "1"),
                new Address(0L, "55-111", "town2", "streetD", "2"),
                new Address(0L, "66-111", "town2", "streetE", "3"),
                new Address(0L, "77-111", "town3", "streetF", "4"),
                new Address(0L, "88-111", "town4", "streetA", "100A"),
                new Address(0L, "99-111", "town5", "streetB", "12C/3"),

        };
    }


}
