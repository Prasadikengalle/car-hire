package lk.ijse.carHire.business.custom;

import lk.ijse.carHire.dto.RentDto;

import java.util.List;

public interface RentBo {

    boolean saveRent(RentDto dto) throws Exception;

    List<RentDto> getAllRents() throws Exception;

    RentDto searchRent(String id) throws  Exception;
}
