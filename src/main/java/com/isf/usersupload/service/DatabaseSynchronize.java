package com.isf.usersupload.service;

import com.isf.usersupload.parser.XmlParser;
import com.isf.usersupload.persistence.model.User;
import com.isf.usersupload.utill.UserKey;


import java.util.*;

/**
 * The class has one public method that synchronizes data with the database.
 */
public class DatabaseSynchronize {

    private XmlParser xmlParser = new XmlParser();
    private UserService userService = new UserService();

    public void synchronize(String fileName) {
        List<User> databaseUsers = userService.getUsers();
        List<User> xmlUsers = xmlParser.parse(fileName);

        Map<UserKey, User> userMap = new HashMap<>();

        for (User user : xmlUsers) {
            userMap.put(new UserKey(user.getDepCode(), user.getDepJob()), user);
        }

        synchronizeDatabase(userMap, databaseUsers);

    }

    private void synchronizeDatabase(Map<UserKey, User> userMap, List<User> databaseUsers) {
        List<User> usersForDelete = new ArrayList<>();
        for (User user : databaseUsers) {
            UserKey userKey = new UserKey(user.getDepCode(), user.getDepJob());
            if (userMap.containsKey(userKey)) {
                if (user.getDescription().equals(userMap.get(userKey).getDescription())) {
                    userMap.remove(userKey);
                } else {
                    user.setDescription(userMap.get(userKey).getDescription());
                    userMap.put(userKey, user);
                }
            } else {
                usersForDelete.add(user);
            }
        }

        userService.synchronizeData(userMap, usersForDelete);
    }
}
