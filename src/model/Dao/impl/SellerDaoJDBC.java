package model.Dao.impl;

import db.DB;
import db.DbException;
import model.Dao.model.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        //it will receive the connection from another class and not here
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement(
                    "insert into seller " +
                            "(Name,Email,BirthDate,BaseSalary,DepartmentID,DepartmentName) " +
                            "values " +
                            "(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1,obj.getName());
            preparedStatement.setString(2,obj.getEmail());
            preparedStatement.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            preparedStatement.setDouble(4,obj.getBaseSalary());
            preparedStatement.setInt(5,obj.getDepartment().getId());
            preparedStatement.setString(6, obj.getDepartment().getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0){
                // if rowsAffected is bigger than 0 means seller was inserted
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()){
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                    //set seller id per getGeneratedKeys
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("[ERROR] no rows affected!");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Department obj) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "Select seller. * , department.Name as DepName " +
                    "From seller inner join department " +
                    "on seller.departmentId = department.Id " +
                    "where seller.Id = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //if next be true mean have the result of query
                Department department = instantiateDepartment(resultSet);
                Seller seller = instatiateSeller(resultSet, department);
                return seller;
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
        return null;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setName(resultSet.getString("DepartmentName"));
        department.setId(resultSet.getInt("DepartmentId"));
        return department;
    }

    private Seller instatiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(department);
        // The seller work in a department , then here make the link between department and seller
        return seller;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "Select  seller.* ,department.Name as DepartmentName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "order by Name";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {
                //if next be true mean have the result of query
                Department department1 = map.get(resultSet.getInt("DepartmentId"));
                //Check if have the department.
                if (department1 == null){
                    //If the result be null instance new department
                    department1 = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"),department1);
                    //save the department
                }
                Seller seller = instatiateSeller(resultSet, department1);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "Select  seller.* ,department.Name as DepartmentName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "where DepartmentId = ? " +
                    "order by Name";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {
                //if next be true mean have the result of query
                Department department1 = map.get(resultSet.getInt("DepartmentId"));
                //Check if have the department.
                if (department1 == null){
                    //If the result be null instance new department
                    department1 = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"),department1);
                    //save the department
                }
                Seller seller = instatiateSeller(resultSet, department);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
