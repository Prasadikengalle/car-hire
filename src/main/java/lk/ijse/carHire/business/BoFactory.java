package lk.ijse.carHire.business;

import lk.ijse.carHire.business.custom.impl.CustomerBoImpl;

public class BoFactory {

    public static <T>T getBo(BoType type){

        switch (type){
            case CUSTOMER :
                return (T) new CustomerBoImpl();

            default:
                return null;
        }
    }
}
