package com.api.parkingcontrol.services;

import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.validation.ParkingSpotValidationImpl;
import com.api.parkingcontrol.stub.ParkingSpotBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class ParkingSpotServiceImplTest {


    @InjectMocks
    private ParkingSpotServiceImpl parkingSpotService;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private ParkingSpotValidationImpl parkingSpotValidation;



    @Test
    @DisplayName("Validate get  Success")
    void getAll() {
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

        Mockito.when(parkingSpotRepository.findAll()).thenReturn(expectGet);

            final var response = parkingSpotService.getAll();
            Assertions.assertThat(expectGet)
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(response);
        }


    @Test
    void getParkingSpotById() {
    }


    @Test
    void save() {
    }




    @Test
    void deleteParkingSpot() {
    }

    @Test
    void updateParkingSpot() {
    }
}