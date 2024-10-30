package com.colak.springtutorial.controller;

import com.colak.springtutorial.dto.request.VehicleLocationRequest;
import com.colak.springtutorial.dto.response.VehicleLocationResponse;
import com.colak.springtutorial.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final GeoLocationService geoLocationService;

    @PostMapping
    public ResponseEntity<Void> setVehicleLocation(@RequestBody VehicleLocationRequest request) {
        geoLocationService.add(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:8080/v1/api/vehicles/nearest?latitude=40.96366196485215&longitude=29.066099435699908&km=10
    @GetMapping("/nearest")
    public ResponseEntity<List<VehicleLocationResponse>> getNearestVehicleLocations(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("km") int km) {

        List<VehicleLocationResponse> vehicleLocations = geoLocationService.findNearestVehicles(longitude, latitude, km);
        return new ResponseEntity<>(vehicleLocations, HttpStatus.OK);
    }

}
