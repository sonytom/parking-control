package com.api.parkingcontrol.repositories;


import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar) throws DataIntegrityViolationException;

    boolean existsByParkingSpotNumber(String parkingSpotNumber) throws DataIntegrityViolationException;

    boolean existsByApartmentAndBlock(String apartment, String block) throws DataIntegrityViolationException;

    boolean existsById (UUID id);

}
