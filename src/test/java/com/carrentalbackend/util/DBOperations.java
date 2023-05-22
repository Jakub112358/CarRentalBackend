package com.carrentalbackend.util;


import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class DBOperations {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;

    public User addSimpleUserToDB() {
        var user = UserFactory.getSimpleUser();
        userRepository.save(user);
        assertTrue(userRepository.existsByEmail(user.getEmail()));
        return user;
    }

    public Client addSimpleClientToDB() {
        var client = ClientFactory.getSimpleClient();
        clientRepository.save(client);
        assertTrue(clientRepository.existsByEmail(ClientFactory.simpleEmail));
        return client;
    }
}
