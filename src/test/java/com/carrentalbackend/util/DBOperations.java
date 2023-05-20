package com.carrentalbackend.util;



import com.carrentalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class DBOperations {
    @Autowired
    private UserRepository userRepository;
    public void addSimpleUserToDB() {
        var user = UserFactory.simpleUser();
        userRepository.save(user);
        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }
}
