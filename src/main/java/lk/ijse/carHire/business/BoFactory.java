package lk.ijse.carHire.business;

import lk.ijse.carHire.business.custom.impl.CategoryBoImpl;
import lk.ijse.carHire.business.custom.impl.CustomerBoImpl;

public class BoFactory {

    public static <T>T getBo(BoType type){

        switch (type){
            case CUSTOMER :
                return (T) new CustomerBoImpl();

            case CATEGORY :
                return (T) new CategoryBoImpl();

            default:
                return null;
        }
    }
}
