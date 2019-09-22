package com.isf.departmentupload;


import com.isf.departmentupload.persistence.model.Department;
import com.isf.departmentupload.service.DepartmentService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DepartmentServiceTest {

    private DepartmentService departmentService = new DepartmentService();

    @Test
    public void getAllDepartmentsTest() {
        List<Department> departments = departmentService.getDepartments();
        Assert.assertFalse(departments.isEmpty());
    }


}
