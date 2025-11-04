package model.Dao.model;

import db.DB;
import model.Dao.impl.DepartmentDaoJDBC;
import model.Dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDAo(){
        //Return the interface type and instance the implementation
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao(){
        //Return the interface type and instance the implementation
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
