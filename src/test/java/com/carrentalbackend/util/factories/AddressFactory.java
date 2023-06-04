package com.carrentalbackend.util.factories;

import com.carrentalbackend.model.entity.Address;

import static com.carrentalbackend.model.entity.Address.AddressBuilder;

public class AddressFactory {
    public static String simpleZipCode = "61-838";
    public static String simpleTown = "Poznan";
    public static String simpleStreet = "Wroclawska";
    public static String simpleHouseNumber = "9";

    public static Address getSimpleAddress(){
        return new Address(null, simpleZipCode, simpleTown, simpleStreet, simpleHouseNumber);
    }

    public static AddressBuilder getSimpleAddressBuilder(){
        return Address.builder()
                .zipCode(simpleZipCode)
                .town(simpleTown)
                .street(simpleStreet)
                .houseNumber(simpleHouseNumber);
    }
}
