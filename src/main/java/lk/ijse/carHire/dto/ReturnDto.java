package lk.ijse.carHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ReturnDto {
    private String returnId;
    private String rentalId;
    private String carId;
    private Double fee;
}
