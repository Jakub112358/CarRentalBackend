package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.clients.rest.ClientCreateRequest;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.enumeration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static com.carrentalbackend.features.clients.rest.ClientCreateRequest.ClientCreateRequestBuilder;

public class ClientFactory {
     private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
     public final static String simpleFirstName = "Jan";
     public final static String simpleLastName = "Kowalski";
     public final static String simpleEmail = "client@mail.com";
     public final static String simplePassword = "password";
     public static ClientCreateRequestBuilder getSimpleClientRequestBuilder(){
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
