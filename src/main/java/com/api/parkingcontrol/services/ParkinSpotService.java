package com.api.parkingcontrol.services;

import org.springframework.stereotype.Service;

@Service
public class ParkinSpotService implements ParkinSpotServiceInterface{

    final ParkinSpotService parkinspotInject;

    public ParkinSpotService(ParkinSpotService parkinspotInject) {
        this.parkinspotInject = parkinspotInject;
    }


    @Override
    public ParkinSpotService save(ParkinSpotService parkinSpotService) {
        return null;
    }

}

