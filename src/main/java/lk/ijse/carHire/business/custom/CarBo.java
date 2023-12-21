package lk.ijse.carHire.business.custom;

import lk.ijse.carHire.dto.CarDto;

import java.util.List;

public interface CarBo {

    boolean saveCar(CarDto dto) throws Exception;

    boolean updateCar(CarDto dto) throws  Exception;

    CarDto searchCar(String id) throws  Exception;

    boolean deleteCar(String id) throws Exception;

    List<CarDto> getAllCars() throws Exception;
}
