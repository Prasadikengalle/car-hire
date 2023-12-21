package lk.ijse.carHire.business;

import lk.ijse.carHire.business.custom.impl.*;

public class BoFactory {

    public static <T>T getBo(BoType type){

        switch (type){
            case CUSTOMER :
                return (T) new CustomerBoImpl();

            case CATEGORY :
                return (T) new CategoryBoImpl();

            case CAR :
                return (T) new CarBoImpl();

            case RENT :
                return (T) new RentBoImpl();

            case RETURN :
                return (T) new ReturnBoImpl();

            default:
                return null;
        }
    }
}
