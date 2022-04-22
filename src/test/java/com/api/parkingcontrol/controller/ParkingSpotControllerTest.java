package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.services.ParkingSpotService;
import com.api.parkingcontrol.stub.ParkingSpotBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotControllerTest {

    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @Test
    @DisplayName("Validate get all Success")
    public void getAllParkingSpotssSccess() {

        final var expectGet = List.of(new ParkingSpotBuilder().
                withId(UUID.randomUUID())
                .withParkingSpotNumber("AAA")
                .withLicensePlateCar("aaa")
                .withBrandCar("bmw")
                .withModelCar("aa")
                .withColorCar("Black")
                .withRegistrationDate(LocalDateTime.now())
                .withResponsibename("Rafaela")
                .withApatment("123")
                .withBlock("2")
                .build());

        Mockito.when(parkingSpotService.getAll()).thenReturn(expectGet);

        final var response = parkingSpotController.getAllParkingSpots();
        Assertions.assertThat(expectGet).isEqualTo(response);
    }

    @Test
    @DisplayName("Validate get  Success")
    public void getAllParkingSpotByIDSuccess() {
        final var expectGet = new ParkingSpotBuilder().
                withId(UUID.randomUUID())
                .withParkingSpotNumber("23")
                .withLicensePlateCar("INX9870A")
                .build();

        Mockito.when(parkingSpotService.getParkingSpotById(expectGet.getId())).thenReturn(expectGet);

        final var response = parkingSpotController.getParkingSpotById(expectGet.getId());

        Assertions.assertThat(expectGet).isEqualTo(response);
    }


}
