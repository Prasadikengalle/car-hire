package lk.ijse.carHire.dao;

import lk.ijse.carHire.dao.custom.impl.CustomerDaoImpl;

public class DaoFacoty {

    public static <T>T getDao(DaoType type){
        switch (type){
            case CUSTOMER :
                return (T) new CustomerDaoImpl();

            default :
                return  null;

        }
    }
}
