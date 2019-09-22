package com.isf.departmentupload;

import com.isf.departmentupload.parser.XmlCreator;
import com.isf.departmentupload.service.DatabaseSynchronize;

public class DepartmentUpload {
    public static void main(String[] args) {
        XmlCreator xmlCreator = new XmlCreator();
        DatabaseSynchronize databaseSynchronize = new DatabaseSynchronize();

        if (args[0].equalsIgnoreCase("create")) {
            xmlCreator.createXmlFile(args[1]);
        }
        if (args[0].equalsIgnoreCase("sync")) {
            databaseSynchronize.synchronize(args[1]);
        }
    }
}