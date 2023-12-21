package lk.ijse.carHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CarDto {


    private String carId;
    private String licensePlate;
    private String make;
    private String model;
    private Integer year;
    private String categoryId;
    private Double dailyRentalRate;
    private String available;
}
