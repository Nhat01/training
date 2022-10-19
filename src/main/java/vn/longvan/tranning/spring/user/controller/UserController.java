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
     * LIỆT KÊ DANH SÁCH TẤT CẢ NGƯỜI ÙNG
     * @param
     * @return List<User>
     */
    public List<User> listAllUsers(){
        return userManager.getAllUser();
    }


}
