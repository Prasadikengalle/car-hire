package lk.ijse.carHire.business.custom.impl;

import lk.ijse.carHire.business.custom.RentBo;
import lk.ijse.carHire.dao.DaoFacoty;
import lk.ijse.carHire.dao.DaoType;
import lk.ijse.carHire.dao.custom.CarDao;
import lk.ijse.carHire.dao.custom.RentDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.RentDto;
import lk.ijse.carHire.entity.CarEntity;
import lk.ijse.carHire.entity.RentEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentBoImpl implements RentBo {

    RentDao rentDao = DaoFacoty.getDao(DaoType.RENT);
    CarDao carDao =DaoFacoty.getDao(DaoType.CAR);
    @Override
    public boolean saveRent(RentDto dto) throws Exception {
        var rentEntity = new RentEntity(
                dto.getId(),
                dto.getCarId(),
                dto.getCustId(),
                dto.getPickupDate(),
                dto.getReturnDate(),
                "No"
        );


        Connection con = null;
        try{
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            if(rentDao.save(rentEntity)){

                CarEntity carEntity = carDao.search(dto.getCarId());
                if( carEntity != null){
                    carEntity.setAvailable("NO");
                    if(carDao.update(carEntity)){
                        con.commit();
                        return true;


                    }else {
                        con.rollback();
                        throw new SQLException("Car Update Error!");

                    }
                }else{
                    con.rollback();

                    throw new SQLException("Rent Save Error!");
                }
            }else{

                throw new SQLException("Rent Saving Failed!");
            }

        }catch (SQLException e){
            if (con != null) {

                con.rollback();

            }
            throw e;
        }finally{
            if( con != null){



            }
        }

    }

    @Override
    public List<RentDto> getAllRents() throws Exception {
        List<RentEntity> rentEntities = rentDao.getAll();

        List<RentDto> dtoList = new ArrayList<>();

        for(RentEntity entity : rentEntities){
            dtoList.add(new RentDto(
                    entity.getId(),

                    entity.getCarId(),
                    entity.getCustId(),
                    entity.getPickupDate(),
                    entity.getReturnDate()

            ));

        }


        return dtoList;
    }

    @Override
    public RentDto searchRent(String id) throws Exception {
        RentEntity entity = rentDao.search(id);

        return  new RentDto(
                entity.getId(),
                entity.getCarId(),
                entity.getCustId(),
                entity.getPickupDate(),
                entity.getReturnDate()
        );
    }
}
