package com.isf.departmentupload;

import com.isf.departmentupload.parser.XmlCreator;
import com.isf.departmentupload.parser.XmlParser;
import com.isf.departmentupload.persistence.model.Department;
import com.isf.departmentupload.service.DatabaseSynchronize;
import com.isf.departmentupload.service.DepartmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class DatabaseSynchronisedTest {

    private DepartmentService departmentService = new DepartmentService();
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
        List<Department> xmlDepartment = xmlParser.parse(fileName);
        databaseSynchronize.synchronize(fileName);
        List<Department> databaseAfterSynchronised = departmentService.getDepartments();
        Assert.assertEquals(xmlDepartment, databaseAfterSynchronised);

    }
}
