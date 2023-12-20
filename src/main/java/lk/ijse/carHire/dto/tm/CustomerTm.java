package lk.ijse.carHire.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {

    private String id;
    private String name;
    private String address;
    private String phone;
    private String zip;

}
