package lk.ijse.carHire.business.custom;

import lk.ijse.carHire.dto.CustomerDto;

import java.util.List;

public interface CustomerBo {
    boolean saveCustomer(CustomerDto dto) throws Exception;

    boolean updateCustomer(CustomerDto dto) throws  Exception;

    CustomerDto searchCustomer(String id) throws  Exception;

    boolean deleteCustomer(String id) throws Exception;

    List<CustomerDto> getAllCustomers() throws Exception;
}
