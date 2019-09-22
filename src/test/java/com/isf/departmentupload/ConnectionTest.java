package com.isf.departmentupload;

import com.isf.departmentupload.utill.Connector;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class ConnectionTest {

    @Test
    public void connectionTest() {
        try {
            Assert.assertNotNull(Connector.openConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
