package com.isf.usersupload;

import com.isf.usersupload.parser.XmlCreator;
import com.isf.usersupload.parser.XmlParser;
import com.isf.usersupload.persistence.model.User;
import com.isf.usersupload.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;


public class ParsersTest {


    private XmlParser xmlParser = new XmlParser();
    private XmlCreator xmlCreator = new XmlCreator();


    private final String fileName = "XmlForTest";
    private final String suffix = ".xml";
    private UserService userService = new UserService();

    @Before
    public void insertUser() {
        userService.insertUser(new User("Защита от темных сил","Должность","Занимается защитой от темных сил"));
    }


    @Test
    public void xmlCreateTest() {
        xmlCreator.createXmlFile(fileName);
        File file = new File("C:\\XML\\" + fileName + suffix);
        Assert.assertTrue(file.exists());
        Assert.assertEquals(fileName + suffix, file.getName());
        Assert.assertTrue(file.length() > 0);
    }

    @Test
    public void xmlParseTest() {
        xmlCreator.createXmlFile(fileName);
        List<User> users = xmlParser.parse(fileName);
        Assert.assertFalse(users.isEmpty());

    }
}
