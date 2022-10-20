package vn.longvan.tranning.spring.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.longvan.tranning.spring.user.manager.UserManager;
import vn.longvan.tranning.spring.user.model.User;

import java.util.List;

@Component
public class UserController {
    @Autowired
    UserManager userManager;

    /**
     * LIỆT KÊ DANH SÁCH TẤT CẢ NGƯỜI DÙNG
     * @param
     * @return List<User>
     */
    public List<User> listAllUsers(){
        return userManager.getAllUser();
    }

    /**
     * LIỆT KÊ NGƯỜI DÙNG THEO userId
     * @param userId
     * @return User
     */
    public User getUserById(String userId){
        return userManager.getUser(userId);
    }

    /**
     * Tạo người dùng
     * @param user
     * @return 
     */
    public void createUser(User user){
        userManager.createUser(user);
    }

    /**
     * Chỉnh sửa tên người dùng
     * @param userId, newName
     * @return
     */
    public void changeNameUser(String userId, String newName){
        userManager.changeUserName(userId, newName);
    }

    /**
     * Tạo người dùng
     * @param userId
     * @return
     */
    public void deleteUser(String userId){
        userManager.deleteUser(userId);
    }
}
