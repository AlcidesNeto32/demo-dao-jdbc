package application;


import model.Dao.model.DaoFactory;
import model.Dao.model.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        List<Department> depart = departmentDao.findAll();


        System.out.println("Test: insert department");
        Department department = new Department(7,"Computer");
        departmentDao.insert(department);
        System.out.println("--------------END TEST---------------");
        System.out.println();

        System.out.println("Test: update department");
        departmentDao.update(new Department(5,"Janitor"));
        System.out.println("--------------END TEST---------------");
        System.out.println();

        System.out.println("Test: deleteById");
        departmentDao.deleteById(6);
        System.out.println("Done!");
        System.out.println("--------------END TEST---------------");
        System.out.println();

        System.out.println("test:  findAll");
        for (Department dp : depart) {
            System.out.println(dp);
        }
        System.out.println("--------------END TEST---------------");
        System.out.println();

        System.out.println("Test: findDepartmentByID");
        System.out.println(departmentDao.findById(6));
        System.out.println("--------------END TEST----------------");
        System.out.println();
    }
}
