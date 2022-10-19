package vn.longvan.tranning.spring.user.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.longvan.tranning.spring.user.controller.UserController;
import vn.longvan.tranning.spring.user.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean(name = "userBean")
@Component
public class UserBean implements Serializable {
    @Autowired
    UserController userController;

    private List<User> users = new ArrayList<>();


    public void loadData(){
        users = userController.listAllUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
