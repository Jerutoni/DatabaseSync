package com.isf.departmentupload;

import com.isf.departmentupload.parser.XmlCreator;
import com.isf.departmentupload.parser.XmlParser;
import com.isf.departmentupload.persistence.model.Department;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;


public class ParsersTest {


    private XmlParser xmlParser = new XmlParser();
    private XmlCreator xmlCreator = new XmlCreator();


    private final String fileName = "XmlForTest";
    private final String suffix = ".xml";


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
        List<Department> departments = xmlParser.parse(fileName);
        Assert.assertFalse(departments.isEmpty());

    }
}
