package lk.ijse.carHire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RentEntity {

    private String id;
    private String carId;
    private String custId;
    private String pickupDate;
    private String returnDate;
    private String isReturned;
}
