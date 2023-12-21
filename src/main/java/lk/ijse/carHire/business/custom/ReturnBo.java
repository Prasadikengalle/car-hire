package lk.ijse.carHire.business.custom;

import lk.ijse.carHire.dto.ReturnDto;

import java.sql.SQLException;
import java.util.List;

public interface ReturnBo {

    boolean saveReturn(ReturnDto dto) throws Exception;

    List<ReturnDto> getAllReturns() throws Exception;

    double calculateFee(String rentaId) throws SQLException;
}
