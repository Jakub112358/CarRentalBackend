package com.carrentalbackend;

import com.carrentalbackend.config.security.JwtService;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.ReservationRepository;
import com.carrentalbackend.repository.UserRepository;
import com.carrentalbackend.util.DBOperations;
import com.carrentalbackend.util.RequestTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BaseIT {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected ReservationRepository reservationRepository;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected JwtService jwtService;
    @Autowired
    protected DBOperations dbOperations;
    @Autowired
    protected RequestTool requestTool;

    @SneakyThrows
    protected <T> String toJsonString(T obj) {
        return objectMapper.writeValueAsString(obj);
    }

}
