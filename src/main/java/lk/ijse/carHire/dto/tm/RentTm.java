package lk.ijse.carHire.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RentTm {

    private String id;
    private String custId;
    private String carId;
    private String pickupDate;
    private String returnDate;
}
