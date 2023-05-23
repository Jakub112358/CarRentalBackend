package com.carrentalbackend.util.factories;

import com.carrentalbackend.model.entity.Address;

public class AddressFactory {
    public static String simpleZipCode = "61-838";
    public static String simpleTown = "Poznan";
    public static String simpleStreet = "Wroclawska";
    public static String simpleHouseNumber = "9";

    public static Address getSimpleAddress(){
        return new Address(0L, simpleZipCode, simpleTown, simpleStreet, simpleHouseNumber);
    }
}
