package com.carrentalbackend.service.util;

import com.carrentalbackend.exception.InvalidCastException;

public class ServiceUtil {
    public static void checkIfInstance(Object object, Class<?> castClass){
        if (!castClass.isInstance(object))
            throw new InvalidCastException(castClass.getSimpleName());
    }
}
