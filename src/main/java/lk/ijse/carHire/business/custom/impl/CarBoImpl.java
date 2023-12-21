package lk.ijse.carHire.business.custom.impl;

import lk.ijse.carHire.business.custom.CarBo;
import lk.ijse.carHire.dao.DaoFacoty;
import lk.ijse.carHire.dao.DaoType;
import lk.ijse.carHire.dao.custom.CarDao;
import lk.ijse.carHire.dto.CarDto;
import lk.ijse.carHire.entity.CarEntity;

import java.util.ArrayList;
import java.util.List;

public class CarBoImpl implements CarBo {

    CarDao dao = DaoFacoty.getDao(DaoType.CAR);
    @Override
    public boolean saveCar(CarDto dto) throws Exception {
        var carEntity = new CarEntity(
                dto.getCarId(),
                dto.getLicensePlate(),
                dto.getMake(),
                dto.getModel(),
                dto.getYear(),
                dto.getCategoryId(),
                dto.getDailyRentalRate(),
                dto.getAvailable()
        );

        return dao.save(carEntity);
    }

    @Override
    public boolean updateCar(CarDto dto) throws Exception {
        var carEntity = new CarEntity(
                dto.getCarId(),
                dto.getLicensePlate(),
                dto.getMake(),
                dto.getModel(),
                dto.getYear(),
                dto.getCategoryId(),
                dto.getDailyRentalRate(),
                dto.getAvailable()
        );


        return dao.update(carEntity);
    }

    @Override
    public CarDto searchCar(String id) throws Exception {
        CarEntity ce = dao.search(id);

        return  new CarDto(
                ce.getCarId(),
                ce.getLicensePlate(),
                ce.getMake(),
                ce.getModel(),
                ce.getYear(),
                ce.getCategoryId(),
                ce.getDailyRentalRate(),
                ce.getAvailable()
        );
    }

    @Override
    public boolean deleteCar(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public List<CarDto> getAllCars() throws Exception {
        List<CarEntity> carEntityList = dao.getAll();

        List<CarDto> carDtoList = new ArrayList<>();

        for(CarEntity carEntity : carEntityList){
            carDtoList.add(new CarDto(
                    carEntity.getCarId(),
                    carEntity.getLicensePlate(),
                    carEntity.getMake(),
                    carEntity.getModel(),
                    carEntity.getYear(),
                    carEntity.getCategoryId(),
                    carEntity.getDailyRentalRate(),
                    carEntity.getAvailable()));

        }
        return carDtoList;
    }
}
