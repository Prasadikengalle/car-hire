package lk.ijse.carHire.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CarTm {
    private String id;
    private String make;
    private String model;
    private Integer year;
    private Double dailyRental;
    private String availability;
}
