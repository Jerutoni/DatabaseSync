package com.isf.usersupload;

import com.isf.usersupload.parser.XmlCreator;
import com.isf.usersupload.parser.XmlParser;
import com.isf.usersupload.persistence.model.User;
import com.isf.usersupload.service.DatabaseSynchronize;
import com.isf.usersupload.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class DatabaseSynchronisedTest {

    private UserService userService = new UserService();
    private XmlParser xmlParser = new XmlParser();
    private DatabaseSynchronize databaseSynchronize = new DatabaseSynchronize();
    private final String fileName = "XmlForTest";
    private XmlCreator xmlCreator = new XmlCreator();

    @Before
    public void createXml() {
        xmlCreator.createXmlFile(fileName);
    }


    @Test
    public void synchronisedDatabaseTest() throws SQLException {
        List<User> xmlUser = xmlParser.parse(fileName);
        databaseSynchronize.synchronize(fileName);
        List<User> databaseAfterSynchronised = userService.getUsers();
        Assert.assertEquals(xmlUser, databaseAfterSynchronised);

    }
}
