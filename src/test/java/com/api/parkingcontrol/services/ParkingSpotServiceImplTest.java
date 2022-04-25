package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.stub.ParkingSpotBuilder;
import com.api.parkingcontrol.validation.ParkingSpotValidationImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

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
    void returnGetAllIfSuccessful() {
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
                .buildModel());
        Mockito.when(parkingSpotRepository.findAll()).thenReturn(expectGet);
        final var response = parkingSpotService.getAll();
        Assertions.assertThat(expectGet)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(response);
    }


    @Test
    @DisplayName("Validate get  Success")
    void returnGetAllParkingSpotByIDIfSuccess() {
        final var expectGet = new ParkingSpotBuilder().
                withId(UUID.randomUUID())
                .withParkingSpotNumber("23")
                .withLicensePlateCar("INX9870A")
                .buildModel();

        Mockito.when(parkingSpotRepository.findById(expectGet.getId())).thenReturn(Optional.of(expectGet));
        final var response = parkingSpotService.getParkingSpotById(expectGet.getId());
        Assertions.assertThat(expectGet).isEqualTo(response);
    }

    @Test
    @DisplayName("validate to update")
    void returnExeptionIfNotfoundforReturnGetAllById() {

        var uuid = UUID.randomUUID();
        Mockito.when(parkingSpotRepository.findById(ArgumentMatchers.any(UUID.class))).
                thenThrow(new ResourceNotFoundException("Not found in database:: " + uuid));

        assertThrows(ResourceNotFoundException.class, () ->
                        parkingSpotService.getParkingSpotById(uuid));

        Mockito.verify(parkingSpotRepository, Mockito.times(1)).findById(uuid);
    }




    @Test
    @DisplayName("validate to save")
    void returnParkingSpotIfSavedSucessful() {

        final var expectGet = new ParkingSpotBuilder()
                .withId(UUID.randomUUID())
                .withParkingSpotNumber("AAA")
                .withLicensePlateCar("aaa")
                .withBrandCar("bmw")
                .withModelCar("aa")
                .withColorCar("Black")
                .withRegistrationDate(LocalDateTime.now())
                .withResponsibename("Rafaela")
                .withApatment("123")
                .withBlock("2")
                .buildModel();

        Mockito.when(parkingSpotRepository.save(ArgumentMatchers.any(ParkingSpotModel.class))).thenReturn(expectGet);
        final var response = new ParkingSpotBuilder()
                .withParkingSpotNumber(expectGet.getParkingSpotNumber())
                .withLicensePlateCar(expectGet.getLicensePlateCar())
                .withBrandCar(expectGet.getBrandCar())
                .withModelCar(expectGet.getModelCar())
                .withColorCar(expectGet.getColorCar())
                .withResponsibename(expectGet.getResponsibleName())
                .withApatment(expectGet.getApartment())
                .withBlock(expectGet.getBlock())
                .buildDto();
        final var cliente = parkingSpotService.save(response);
        Assertions.assertThat(cliente).isEqualTo(expectGet);
    }

    @Test
    @DisplayName("validate to update")
    void returnParkingspotmodelIfUpdadeSucefull() {

        final var expectGet = new ParkingSpotBuilder()
                .withId(UUID.randomUUID())
                .withParkingSpotNumber("AAA")
                .withLicensePlateCar("aaa")
                .buildModel();

        Mockito.when(parkingSpotRepository.findById(expectGet.getId())).thenReturn(Optional.of(expectGet));
        Mockito.when(parkingSpotRepository.save(ArgumentMatchers.any(ParkingSpotModel.class))).thenReturn(expectGet);
        final var response = new ParkingSpotBuilder().withParkingSpotNumber(expectGet.getParkingSpotNumber()).buildDto();

        final var cliente = parkingSpotService.updateParkingSpot(expectGet.getId(), response);

        Assertions.assertThat(cliente).isNotNull().isEqualTo(expectGet);
    }

    @Test
    @DisplayName("validate to update")
    void returnParkingspotmodelIfIdNotFound() {

            var uuid = UUID.randomUUID();
        Mockito.when(parkingSpotRepository.findById(ArgumentMatchers.any(UUID.class))).
                thenThrow(new ResourceNotFoundException("Not found in database:: " + uuid));

        assertThrows(ResourceNotFoundException.class, () ->  parkingSpotService. updateParkingSpot
                (uuid, new ParkingSpotDto()));

        Mockito.verify(parkingSpotRepository, Mockito.times(1)).findById(uuid);
    }


    @Test
    @DisplayName("validate to delete")
    void returnDeletedParkingSpotSucessfull() {

        final var expectGet = new ParkingSpotBuilder()
                .withId(UUID.randomUUID())
                .withParkingSpotNumber("AAA")
                .withLicensePlateCar("aaa")
                .buildModel();

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        Mockito.when(parkingSpotRepository.findById(expectGet.getId())).thenReturn(Optional.of(expectGet));
        final var cliente = parkingSpotService.deleteParkingSpot(expectGet.getId());
        Assertions.assertThat(cliente).isNotNull().isEqualTo(response);
    }


    @Test
    @DisplayName("validate to delete")
    void notReturnDeletedParkingNotFound() {

        var parkinspotDto = new ParkingSpotBuilder().withId(UUID.randomUUID()).buildModel();

        var uuid = UUID.randomUUID();
        Mockito.when(parkingSpotRepository.findById(ArgumentMatchers.any(UUID.class))).
                thenThrow(new ResourceNotFoundException("Not found in database"));

        assertThrows(ResourceNotFoundException.class, () ->  parkingSpotService.deleteParkingSpot(uuid));

        Mockito.verify(parkingSpotRepository, Mockito.times(1)).findById(uuid);



    }



}