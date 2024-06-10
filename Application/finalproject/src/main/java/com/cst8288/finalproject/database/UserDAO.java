package com.cst8288.finalproject.database;

import com.cst8288.finalproject.users.User;

public interface UserDAO {

    public void createUser(User user);
    public User retrieveUser(String email);
    public void updateUser(String email);
    public void deleteUser(String email);

}
