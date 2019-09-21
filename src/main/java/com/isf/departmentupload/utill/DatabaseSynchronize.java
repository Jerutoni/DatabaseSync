package com.isf.departmentupload.utill;

import com.isf.departmentupload.parser.XmlParser;
import com.isf.departmentupload.persistence.model.Department;
import com.isf.departmentupload.service.DepartmentService;
import org.apache.log4j.Logger;

import java.util.*;

public class DatabaseSynchronize {

    private final static Logger log = Logger.getLogger(DatabaseSynchronize.class);
    private XmlParser xmlParser = new XmlParser();
    private DepartmentService databaseService = new DepartmentService();

    public void databaseSynchronise(String fileName) {
        Set<Department> databaseDepartments = databaseService.getDepartments();
        List<Department> xmlDepartments = xmlParser.parse(fileName);

        Map<DepartmentKey, Department> departmentMap = new HashMap<>();

        for (Department department : xmlDepartments) {
            departmentMap.put(new DepartmentKey(department.getDepCode(), department.getDepJob()), department);
        }

        synchroniseDatabase(departmentMap, databaseDepartments);
        log.info("Database Synchronize successful");
    }

    private void synchroniseDatabase(Map<DepartmentKey, Department> departmentMap, Set<Department> databaseDepartments) {
        List<Department> departmentsForDelete = new ArrayList<>();
        for (Department dep : databaseDepartments) {
            DepartmentKey departmentKey = new DepartmentKey(dep.getDepCode(), dep.getDepJob());
            if (departmentMap.containsKey(departmentKey)) {
                if (dep.getDescription().equals(departmentMap.get(departmentKey).getDescription())) {
                    departmentMap.remove(departmentKey);
                } else {
                    dep.setDescription(departmentMap.get(departmentKey).getDescription());
                    departmentMap.put(departmentKey, dep);
                }
            } else {
                departmentsForDelete.add(dep);
            }
        }

        databaseService.synchronizeData(departmentMap, departmentsForDelete);
    }
}
