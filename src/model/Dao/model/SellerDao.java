package model.Dao.model;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert (Seller obj);
    void update (Seller obj);
    void deleteById (Department obj);
    Seller findById (Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
