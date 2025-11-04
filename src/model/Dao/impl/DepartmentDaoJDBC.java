package model.Dao.impl;

import db.DB;
import db.DbException;
import model.Dao.model.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    Connection connection;

    public DepartmentDaoJDBC() {

    }

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void insert(Department obj) {
        PreparedStatement preparedStatement = null;

        try {
            String query = "insert into Department " +
                    "(Id,Name)" +
                    "values " +
                    "(?,?) ";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, obj.getId());
            preparedStatement.setString(2, obj.getName());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Insert Done!");
            } else {
                throw new DbException("[ERROR] unexpected error happened!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement preparedStatement = null;

        try {
            String query = "update department " +
                    "set Name = ? " +
                    "where Id = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setInt(2, obj.getId());
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Update done!");
            } else {
                throw new DbException("[ERROR] unexpected error happened!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            String query = "Delete from department " +
                    "where Id = ? ";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                throw new DbException("[ERROR] this id does not exist!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            String query = "select * from department " +
                    "where id = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Department department = new Department();
            if (resultSet.next()){
                department.setId(resultSet.getInt("Id"));
                department.setName(resultSet.getString("Name"));
                return department;
            } else{
                return null;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        try {
            String query = "select * from Department ";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                List<Department> departmentList = new ArrayList<>();
                Department dp1 = new Department();
                dp1.setId(resultSet.getInt("Id"));
                dp1.setName(resultSet.getString("Name"));
                departmentList.add(dp1);

                while (resultSet.next()) {
                    Department dp2 = new Department();
                    dp2.setName(resultSet.getString("Name"));
                    dp2.setId(resultSet.getInt("Id"));
                    departmentList.add(dp2);
                }
                return departmentList;
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }
}
