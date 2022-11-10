package fr.kirikou.Dashboard.dto;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class CityLocationDTO {
    private double lat;
    private double lon;
    private String city;
    private @Nullable String state;
    private String country;
}
