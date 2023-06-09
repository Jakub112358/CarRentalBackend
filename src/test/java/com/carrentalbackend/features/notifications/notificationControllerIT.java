package com.carrentalbackend.features.notifications;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.util.factories.ClientFactory;
import com.carrentalbackend.util.factories.EmployeeFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.NOTIFICATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class notificationControllerIT extends BaseIT {

    @BeforeEach
    void setUp() {
        dbOperations.cleanOfficeTable();
        dbOperations.cleanUserTable();

    }

    @Test
    @WithMockUser(roles = "CLIENT", username = ClientFactory.simpleEmail)
    public void whenFindAll_asClient_thenCorrectAnswer() throws Exception {
        //given
        dbOperations.addSimpleClientToDB();

        //and
        addOfficeWithEmployee(JobPosition.MANAGER);
        addOfficeWithEmployee(JobPosition.SELLER);

        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(0, notifications.size());
    }


    @Test
    @WithMockUser(roles = "EMPLOYEE", username = EmployeeFactory.simpleEmployeeEmail)
    public void whenFindAll_asEmployee_thenCorrectAnswer() throws Exception {
        //given
        addOfficeWithEmployee(JobPosition.SELLER);

        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(0, notifications.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_asAdmin_thenCorrectAnswer() throws Exception {
        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(0, notifications.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_asAdmin_thenGetIncorrectManagerNumbersNotification() throws Exception {
        //given
        addOfficeWithEmployee(JobPosition.MANAGER);
        addOfficeWithEmployee(JobPosition.SELLER);

        //adding extra office without any employee
        dbOperations.addSimpleOfficeToDB();

        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(2, notifications.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_asAdmin_thenGetCarWithNullOfficeNotification() throws Exception {
        //given
        var office = addOfficeWithEmployee(JobPosition.MANAGER);
        var priceList = dbOperations.addSimplePriceListToDb();

        //and
        dbOperations.addSimpleCarToDb(office, priceList);
        dbOperations.addSimpleCarToDb(office, priceList);
        var car3 = dbOperations.addSimpleCarToDb(office, priceList);

        //and
        car3.setCurrentOffice(null);

        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(1, notifications.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_asAdmin_thenGetEmployeeWithNullOfficeNotification() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        dbOperations.addSimpleEmployeeToDB(office);
        dbOperations.addSimpleEmployeeToDB(null);
        dbOperations.addSimpleEmployeeToDB(null);


        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(2, notifications.size());
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = "notificationTest@username.com")
    public void whenFindAll_asClient_thenGetReservationNotification() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        //and
        var client1 = dbOperations.addSimpleClientToDB();
        client1.setEmail("notificationTest@username.com");

        //and
        var client2 = dbOperations.addSimpleClientToDB();

        //and
        var reservation1 = dbOperations.addSimpleReservationToDB(client1, car, office, office);
        reservation1.setStatus(ReservationStatus.PLANNED);

        //and
        var reservation2 = dbOperations.addSimpleReservationToDB(client1, car, office, office);
        reservation2.setStatus(ReservationStatus.CANCELLED);
        var reservation3 = dbOperations.addSimpleReservationToDB(client1, car, office, office);
        reservation3.setStatus(ReservationStatus.REALIZED);
        var reservation4 = dbOperations.addSimpleReservationToDB(client2, car, office, office);
        reservation4.setStatus(ReservationStatus.PLANNED);


        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(1, notifications.size());
    }


    @Test
    @WithMockUser(roles = "EMPLOYEE", username = "test@username.com")
    public void whenFindAll_asEmployee_thenGetIncomingPickUpNotification() throws Exception {
        //given
        var office1 = dbOperations.addSimpleOfficeToDB();
        var employee1 = dbOperations.addSimpleEmployeeToDB(office1);
        employee1.setEmail("test@username.com");

        //and
        var office2 = dbOperations.addSimpleOfficeToDB();

        //and
        var client = dbOperations.addSimpleClientToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office1, pricelist);

        //and should get notifications
        addReservationWithPickUpAndCarReturn(client, car, office1, office1);
        addReservationWithPickUpAndCarReturn(client, car, office1, office2);

        //and shouldn't get notifications
        addReservationWithPickUpAndCarReturn(client, car, office2, office2);
        addReservationWithPickUpAndCarReturn(client, car, office2, office2);
        addReservationWithPickUpAndCarReturn(client, car, office2, office2);
        addReservationWithPickUpAndCarReturn(client, car, office2, office1);
        addReservationWithPickUpAndCarReturn(client, car, office2, office1);
        addReservationWithPickUpAndCarReturn(client, car, office2, office1);

        //when
        var result = requestTool.sendGetRequest(NOTIFICATION);

        //then
        result.andExpect(status().isOk());

        //and
        Set<NotificationDTO> notifications = extractNotifications(result);
        assertEquals(2, notifications.size());
    }


    private Office addOfficeWithEmployee(JobPosition jobPosition) {
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        employee.setJobPosition(jobPosition);
        return office;
    }

    private Set<NotificationDTO> extractNotifications(ResultActions result) throws Exception {
        var responseJson = result.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(responseJson, new TypeReference<>() {
        });
    }

    private void addReservationWithPickUpAndCarReturn(Client client, Car car, Office pickUpOffice, Office returnOffice) {
        var reservation = dbOperations.addSimpleReservationToDB(client, car, pickUpOffice, returnOffice);
        reservation.setPickUp(createCarPickUp(reservation, car, pickUpOffice));
        reservation.setCarReturn(createCarReturn(reservation, car, returnOffice));
    }

    private CarReturn createCarReturn(Reservation reservation, Car car, Office office) {
        return CarReturn.builder()
                .plannedReturnDate(reservation.getDateTo())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .office(office)
                .build();
    }

    private PickUp createCarPickUp(Reservation reservation, Car car, Office office) {
        return PickUp.builder()
                .plannedPickUpDate(reservation.getDateFrom())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .office(office)
                .build();
    }


}

