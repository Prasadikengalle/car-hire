package lk.ijse.carHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerDto {
    private String id;
    private String title;
    private String name;
    private String dob;
    private String phone;
    private String address;
    private String city;
    private String province;
    private String zip;
}
