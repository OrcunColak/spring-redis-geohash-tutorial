package com.colak.springtutorial.config;

import com.colak.springtutorial.dto.request.VehicleLocationRequest;
import com.colak.springtutorial.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationRunner implements CommandLineRunner {

    private final GeoLocationService geoLocationService;

    @Override
    public void run(String... args) {

        geoLocationService.add(VehicleLocationRequest.builder()
                .latitude(40.9635357987984)
                .longitude(29.115110861465435)
                .vehicleName("İçeren Köy Merkez Taksi").build());

        geoLocationService.add(VehicleLocationRequest.builder()
                .latitude(40.969278607807425)
                .longitude(29.09265249930892)
                .vehicleName("Alp Taksi").build());

        geoLocationService.add(VehicleLocationRequest.builder()
                .latitude(40.977905060727764)
                .longitude(29.053039078914768)
                .vehicleName("18 Mart Taksi").build());

    }
}
