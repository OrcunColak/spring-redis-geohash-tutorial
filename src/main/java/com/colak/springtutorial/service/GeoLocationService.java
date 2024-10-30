package com.colak.springtutorial.service;

import com.colak.springtutorial.dto.request.VehicleLocationRequest;
import com.colak.springtutorial.dto.response.VehicleLocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private final GeoOperations<String, String> geoOperations;
    public static final String vehicleLocation = "vehicle_location";

    public void add(VehicleLocationRequest request) {
        Point point = new Point(request.getLongitude(), request.getLatitude());
        geoOperations.add(vehicleLocation, point, request.getVehicleName());
    }

    public List<VehicleLocationResponse> findNearestVehicles(Double longitude, Double latitude, int km) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeCoordinates()
                .includeDistance()
                .sortAscending()
                .limit(10);

        Circle circle = new Circle(new Point(longitude, latitude), new Distance(km, Metrics.KILOMETERS));
        GeoResults<RedisGeoCommands.GeoLocation<String>> response = geoOperations.radius(vehicleLocation, circle, args);

        List<VehicleLocationResponse> vehicleLocationResponses = new ArrayList<>();
        assert response != null;
        response.getContent().forEach(data -> {
            List<String> hashList = geoOperations.hash(vehicleLocation, data.getContent().getName());
            assert hashList != null;

            VehicleLocationResponse vehicleLocationResponse = VehicleLocationResponse.builder()
                    .vehicleName(data.getContent().getName())
                    .averageDistance(data.getDistance().toString())
                    .point(data.getContent().getPoint())
                    .hash(hashList.getFirst())
                    .build();
            vehicleLocationResponses.add(vehicleLocationResponse);
        });

        return vehicleLocationResponses;
    }
}
