package vn.longvan.tranning.spring.user.bean;

import org.springframework.beans.factory.annotation.Autowired;
import vn.longvan.tranning.spring.user.controller.UserController;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
public class User implements Serializable {
    @Autowired
    UserController userController;

    private List<User> users = new ArrayList<>();

    public User(){
        users = userController.listAllUsers();
    }
}
