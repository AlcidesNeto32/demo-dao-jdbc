package application;

import model.Dao.model.DaoFactory;
import model.Dao.model.SellerDao;
import model.entities.Seller;

import javax.swing.*;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDAo();
        Seller seller = sellerDao.findById(3);
        JOptionPane.showMessageDialog(null,seller);
    }
}
