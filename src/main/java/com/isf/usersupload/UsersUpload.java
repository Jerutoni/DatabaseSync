package com.isf.usersupload;

import com.isf.usersupload.parser.XmlCreator;
import com.isf.usersupload.service.DatabaseSynchronize;

public class UsersUpload {
    public static void main(String[] args) {
        XmlCreator xmlCreator = new XmlCreator();
        DatabaseSynchronize databaseSynchronize = new DatabaseSynchronize();

        if (args[0].equalsIgnoreCase("create")) {
            xmlCreator.createXmlFile(args[1]);
        } else
        if (args[0].equalsIgnoreCase("sync")) {
            databaseSynchronize.synchronize(args[1]);
        } else {
            throw new RuntimeException("Invalid command Name");
        }
    }
}