package lk.ijse.carHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RentDto {
    private String id;
    private String carId;
    private String custId;
    private String pickupDate;
    private String returnDate;
}
