package com.isf.usersupload;


import com.isf.usersupload.persistence.model.User;
import com.isf.usersupload.service.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {

    private UserService userService = new UserService();

    @Test
    public void getAllDepartmentsTest() {
        List<User> users = userService.getUsers();
        Assert.assertFalse(users.isEmpty());
    }


}
