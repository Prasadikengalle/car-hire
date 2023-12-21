package lk.ijse.carHire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CarEntity {

    private String carId;
    private String licensePlate;
    private String make;
    private String model;
    private Integer year;
    private String categoryId;
    private Double dailyRentalRate;
    private String available;
}
