package application;

import model.Dao.model.DaoFactory;
import model.Dao.model.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDAo();
        System.out.println("Test: seller findById");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println("Test : seller findByDepartment");
        Department department = new Department(2,null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller sel: list){
            System.out.println(sel);
        }
        System.out.println("Test : seller findAll");
        list = sellerDao.findAll();
        for (Seller sel: list){
            System.out.println(sel);
        }

        System.out.println("Test: seller insert");
        Seller seller1 = new Seller(null,"Greg","Greg223@gmail.com",new Date(),4000.00,new Department(2,"Janitor"));
        sellerDao.insert(seller1);
        System.out.println("Inserted! new id = " + seller1.getId());
    }
}
