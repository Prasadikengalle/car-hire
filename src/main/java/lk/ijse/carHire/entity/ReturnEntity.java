package lk.ijse.carHire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ReturnEntity {

    private String returnId;
    private String rentalId;
    private String carId;
    private Double fee;
}
