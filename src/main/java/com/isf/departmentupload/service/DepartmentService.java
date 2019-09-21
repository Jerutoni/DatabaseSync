package com.isf.departmentupload.service;


import com.isf.departmentupload.utill.Connector;
import com.isf.departmentupload.persistence.model.Department;
import com.isf.departmentupload.utill.DepartmentKey;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DepartmentService {
    private static final Logger log = Logger.getLogger(DepartmentService.class);

    private final String INSERT_DEPARTMENT = "INSERT INTO departments (dep_code,dep_job,description) values (?,?,?);";
    private final String UPDATE_DEPARTMENT = "UPDATE departments set description = ? where id = ?;";
    private final String DELETE_DEPARTMENT = "DELETE FROM departments WHERE id = ?;";

    public HashSet<Department> getDepartments() {
        String GET_ALL_DEPARTMENTS = "SELECT * FROM Departments";
        HashSet<Department> departments = new HashSet<>();

        try (PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(GET_ALL_DEPARTMENTS)) {

            try (ResultSet res = preparedStatement.executeQuery()) {
                while (res.next()) {
                    int id = res.getInt("id");
                    String depCode = res.getString("dep_code");
                    String depJob = res.getString("dep_job");
                    String description = res.getString("description");
                    departments.add(new Department(id, depCode, depJob, description));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public void synchronizeData(Map<DepartmentKey, Department> departmentsForUpdate, List<Department> departmentsForDelete) {

        try (Connection connection = Connector.getConnection()) {

            try {
                connection.setAutoCommit(false);
                try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_DEPARTMENT);
                     PreparedStatement updateStatement = connection.prepareStatement(UPDATE_DEPARTMENT);
                     PreparedStatement deleteStatement = connection.prepareStatement(DELETE_DEPARTMENT)) {
                    for (Department department : departmentsForUpdate.values()) {
                        if (department.getId() > 0) {
                            updateStatement.setString(1, department.getDescription());
                            updateStatement.setInt(2, department.getId());
                            updateStatement.addBatch();
                        } else {
                            insertStatement.setString(1, department.getDepCode());
                            insertStatement.setString(2, department.getDepJob());
                            insertStatement.setString(3, department.getDescription());
                            insertStatement.addBatch();
                        }
                    }

                    for (Department department : departmentsForDelete) {
                        deleteStatement.setInt(1, department.getId());
                        deleteStatement.addBatch();
                    }

                    insertStatement.executeBatch();
                    updateStatement.executeBatch();
                    deleteStatement.executeBatch();
                    connection.commit();


                }

            } catch (SQLException e) {
                log.error("Department synchronise failed,going to rollback : " + e);
                connection.rollback();
            }

        } catch (SQLException e) {
            log.error("Department synchronise failed: " + e);
        }
    }
}
