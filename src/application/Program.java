package application;

import model.Dao.model.DaoFactory;
import model.Dao.model.SellerDao;
import model.entities.Seller;


public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDAo();
        System.out.println("Test: seller findById");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
    }
}
