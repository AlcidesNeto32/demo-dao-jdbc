package model.Dao.model;

import model.Dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDAo(){
        //Return the interface type and instance the implementation
        return new SellerDaoJDBC();
    }
}
