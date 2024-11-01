package com.colak.springtutorial.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleLocationRequest {

    private String vehicleName;
    private double latitude;
    private double longitude;
}
