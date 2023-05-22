package com.carrentalbackend.util;

import com.carrentalbackend.features.clients.register.ClientCreateRequest;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.enumeration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static com.carrentalbackend.features.clients.register.ClientCreateRequest.ClientCreateRequestBuilder;

public class ClientFactory {
     private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
     public static String simpleFirstName = "Jan";
     public static String simpleLastName = "Kowalski";
     public static String simpleEmail = "client@mail.com";
     public static String simplePassword = "password";
     public static ClientCreateRequestBuilder getSimpleClientCreateRequestBuilder(){
          return ClientCreateRequest.builder()
                  .firstName(simpleFirstName)
                  .lastName(simpleLastName)
                  .email(simpleEmail)
                  .address(AddressFactory.getSimpleAddress())
                  .password(simplePassword);
     }

     public static Client getSimpleClient(){
          return new Client(0L,
                  simpleEmail,
                  passwordEncoder.encode(simplePassword),
                  Role.CLIENT,
                  simpleFirstName,
                  simpleLastName,
                  AddressFactory.getSimpleAddress(),
                  new ArrayList<>());
     }
}
