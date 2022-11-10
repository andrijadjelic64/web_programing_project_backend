package com.example.web_jul.repositories.user;

import com.example.web_jul.entities.User;

import java.util.List;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.repositories.user.
 */
public interface UserRepository {
    public List<User> getUsers(int page);
    public User findUserById(Integer id);
    public User findUserByEmail(String email);
    public Boolean updateUser(User user);
    public User insertUser(User user);
    public boolean deleteUser(Integer id);
}