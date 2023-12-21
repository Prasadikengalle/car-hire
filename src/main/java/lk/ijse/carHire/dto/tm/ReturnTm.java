package lk.ijse.carHire.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ReturnTm {

    private String returnId;
    private String rentalId;
    private String carId;
    private Double fee;
}
