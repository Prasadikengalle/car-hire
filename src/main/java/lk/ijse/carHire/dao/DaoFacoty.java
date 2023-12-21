package lk.ijse.carHire.dao;

import lk.ijse.carHire.business.custom.impl.ReturnBoImpl;
import lk.ijse.carHire.dao.custom.impl.*;

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

            case RETURN :
                return (T) new ReturnDaoImpl();

            default :
                return  null;

        }
    }
}
