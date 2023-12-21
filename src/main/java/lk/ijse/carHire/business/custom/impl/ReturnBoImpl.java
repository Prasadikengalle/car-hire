package lk.ijse.carHire.business.custom.impl;

import lk.ijse.carHire.business.custom.ReturnBo;
import lk.ijse.carHire.dao.DaoFacoty;
import lk.ijse.carHire.dao.DaoType;
import lk.ijse.carHire.dao.custom.CarDao;
import lk.ijse.carHire.dao.custom.RentDao;
import lk.ijse.carHire.dao.custom.ReturnDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.ReturnDto;
import lk.ijse.carHire.entity.CarEntity;
import lk.ijse.carHire.entity.RentEntity;
import lk.ijse.carHire.entity.ReturnEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReturnBoImpl implements ReturnBo {

    RentDao rentDao = DaoFacoty.getDao(DaoType.RENT);
    ReturnDao returnDao = DaoFacoty.getDao(DaoType.RETURN);
    CarDao carDao = DaoFacoty.getDao(DaoType.CAR);
    @Override
    public boolean saveReturn(ReturnDto dto) throws Exception {
        double fee = calculateFee(dto.getRentalId());

        var returnEntity = new ReturnEntity(
                dto.getReturnId(),
                dto.getRentalId(),
                dto.getCarId(),
                fee
        );

        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);



            if (returnDao.save(returnEntity)) {

                CarEntity carEntity = carDao.search(dto.getCarId());
                if (carEntity != null) {
                    carEntity.setAvailable("Yes");
                    if (carDao.update(carEntity)) {
                        //  connection.commit();
                        RentEntity rentEntity =rentDao.search(dto.getRentalId()) ;
                        if(rentEntity !=null){
                            rentEntity.setIsReturned("Yes");
                            if(rentDao.update(rentEntity)){
                                connection.commit();
                                return true;
                            }else{
                                connection.rollback();
                                throw new SQLException("Rent Update Error!");
                            }
                        }else {
                            connection.rollback();
                            throw new SQLException("Rent save Error!");
                        }

                    } else {
                        connection.rollback();
                        throw new SQLException("Car Update Error!");
                    }

                }else{
                    connection.rollback();

                    throw new SQLException("Return Save Error!");
                }



            } else {
                throw new SQLException("Return Saving Failed!");
            }

        } catch(SQLException e){
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally{
            if (connection != null) {
                //  connection.close();
            }
        }
    }

    @Override
    public List<ReturnDto> getAllReturns() throws Exception {
        List<ReturnEntity> returnEntities = returnDao.getAll();

        List<ReturnDto> dtoList = new ArrayList<>();

        for(ReturnEntity entity : returnEntities){
            dtoList.add(new ReturnDto(
                    entity.getReturnId(),
                    entity.getRentalId(),
                    entity.getCarId(),
                    entity.getFee()
            ));

        }
        return dtoList;
    }

    @Override
    public double calculateFee(String rentaId) throws SQLException {
        double fee = 0.0;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            RentEntity rentEntity = rentDao.search(rentaId);
            if(rentEntity == null){
                throw new SQLException("Rental  not found");
            }

            CarEntity carEntity = carDao.search(rentEntity.getCustId());
            if(carEntity == null){
                throw new SQLException("Car  not found");
            }

            LocalDate pickupDate = LocalDate.parse(rentEntity.getPickupDate());
            LocalDate returnDate = LocalDate.parse(rentEntity.getReturnDate());
            long days = ChronoUnit.DAYS.between(pickupDate,returnDate);

            fee = carEntity.getDailyRentalRate() * days;

            connection.commit();
            return fee;


        } catch (SQLException e) {
            if(connection != null){
                connection.rollback();
            }
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(connection != null){
                //   connection.close();
            }
        }
    }
}
