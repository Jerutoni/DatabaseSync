package com.isf.usersupload.service;


import com.isf.usersupload.utill.Connector;
import com.isf.usersupload.persistence.model.User;
import com.isf.usersupload.utill.UserKey;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * The UserService сlass for manipulating data in a database
 */
public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);

    private final String INSERT_USER = "INSERT INTO users (dep_code,dep_job,description) values (?,?,?);";
    private final String UPDATE_USER = "UPDATE users set description = ? where id = ?;";
    private final String DELETE_USER = "DELETE FROM users WHERE id = ?;";


    /**
     * @return all Users from the database
     */
    public List<User> getUsers() {
        String GET_ALL_DEPARTMENTS = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        Connection connection = null;

        try {
            connection = Connector.openConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DEPARTMENTS)) {

                try (ResultSet res = preparedStatement.executeQuery()) {
                    while (res.next()) {
                        int id = res.getInt("id");
                        String depCode = res.getString("dep_code");
                        String depJob = res.getString("dep_job");
                        String description = res.getString("description");
                        users.add(new User(id, depCode, depJob, description));
                    }
                }

            }

        } catch (SQLException e) {
            log.error("Failed to get all users: " + e);
        } finally {
            try {
                Objects.requireNonNull(connection).close();
            } catch (SQLException ex) {
                log.error("Connection close error: " + ex);
            }
        }

        return users;
    }

    public void insertUser(User user) {
        Connection connection = null;
        try {
            connection = Connector.openConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER) ) {
                preparedStatement.setString(1, user.getDepCode());
                preparedStatement.setString(2, user.getDepJob());
                preparedStatement.setString(3, user.getDescription());
                preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            log.error("Insert user error: " + e);
        } finally {
            try {
                Objects.requireNonNull(connection).close();
            } catch (SQLException e) {
                log.error("Connection close error: " +e);
            }
        }

    }


    /**
     * Method The method synchronizes the database from the xml file within one transaction
     *
     * @param usersForUpdate - Map in which data is stored either for updating or for adding,depending on id
     * @param usersForDelete - List in which data to be deleted from the database is stored
     */
    void synchronizeData(Map<UserKey, User> usersForUpdate, List<User> usersForDelete) {
        boolean bSuccess = false;
        Connection connection = null;
        try {
            connection = Connector.openConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_USER);
                 PreparedStatement updateStatement = connection.prepareStatement(UPDATE_USER);
                 PreparedStatement deleteStatement = connection.prepareStatement(DELETE_USER)) {
                for (User user : usersForUpdate.values()) {
                    if (user.getId() > 0) {
                        updateStatement.setString(1, user.getDescription());
                        updateStatement.setInt(2, user.getId());
                        updateStatement.addBatch();
                    } else {
                        insertStatement.setString(1, user.getDepCode());
                        insertStatement.setString(2, user.getDepJob());
                        insertStatement.setString(3, user.getDescription());
                        insertStatement.addBatch();
                    }
                }

                for (User user : usersForDelete) {
                    deleteStatement.setInt(1, user.getId());
                    deleteStatement.addBatch();
                }

                insertStatement.executeBatch();
                updateStatement.executeBatch();
                deleteStatement.executeBatch();
                bSuccess = true;
            }

        } catch (SQLException e) {
            log.error("Users synchronise failed,going to rollback : " + e);
        } finally {
            try {
                if (connection != null) {
                    if (bSuccess) {
                        connection.commit();
                        log.info("Database Synchronize successful ");
                    } else {
                        connection.rollback();
                    }
                }
                Objects.requireNonNull(connection).close();
            } catch (SQLException ex) {
                log.error("Connection Rollback failed");
            }
        }
    }
}
