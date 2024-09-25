package vn.longvan.tranning.spring.user.controller;

import org.primefaces.model.FilterMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.longvan.tranning.spring.user.manager.UserManager;
import vn.longvan.tranning.spring.user.model.User;

import java.util.List;
import java.util.Map;

@Component
public class UserController {
    private UserManager userManager;

    public UserController(UserManager userManager){
        this.userManager = userManager;
    }

    public void createUser(User user) {
        userManager.createUser(user);
    }

    public List<User> getAllUser() {
        return userManager.getAllUser();
    }

    public List<User> getUserByName(String name){
        return userManager.findByName(name);
    }

    public User getUserById(String id){
        return userManager.getUser(id);
    }

    public void changeUserName(String userId, String newName) {
        userManager.changeUserName(userId, newName);
    }

    public void deleteUser(String userId) {
        userManager.deleteUser(userId);
    }

    public List<User> getUsers(int offset, int pageSize) {
        return userManager.getUsers(offset, pageSize);
    }

    public int countUsers() {
        return userManager.countUsers();
    }

}
