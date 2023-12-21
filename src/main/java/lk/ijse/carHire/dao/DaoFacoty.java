package lk.ijse.carHire.dao;

import lk.ijse.carHire.dao.custom.impl.CarDaoImpl;
import lk.ijse.carHire.dao.custom.impl.CategoryDaoImpl;
import lk.ijse.carHire.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.carHire.dao.custom.impl.RentDaoImpl;

public class DaoFacoty {

    public static <T>T getDao(DaoType type){
        switch (type){
            case CUSTOMER :
                return (T) new CustomerDaoImpl();

            case CATEGORY :
                return (T) new CategoryDaoImpl();

            case CAR :
                return (T) new CarDaoImpl();

            case RENT :
                return (T) new RentDaoImpl();
            default :
                return  null;

        }
    }
}
