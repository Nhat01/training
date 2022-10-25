package vn.longvan.tranning.spring.user.bean;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.longvan.tranning.spring.user.controller.UserController;
import vn.longvan.tranning.spring.user.lazy.LazyUserDataModel;
import vn.longvan.tranning.spring.user.model.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "userBean")
@SessionScoped
@Component
public class UserBean implements Serializable {
    @Autowired
    UserController userController;

    private User user = new User();

    private List<User> users = new ArrayList<>();

    public LazyDataModel<User> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<User> lazyModel) {
        this.lazyModel = lazyModel;
    }

    private LazyDataModel<User> lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = new LazyUserDataModel(userController.listAllUsers());
    }

    public void loadData(){
        lazyModel = new LazyUserDataModel(userController.listAllUsers());
    }

    public void addUser(){
        user = new User();
    }

    public String loadUserDetail(String userId) {
        user = userController.getUserById(userId);
        return "userDetail?faces-redirect=true";
    }

    public String createUser(){
        userController.createUser(user);
        user = new User();
        return "userDetail";
    }

    public String changeNameUser(){
        userController.changeNameUser(user.getId(), user.getName());
        return "userDetail?faces-redirect=true";
    }

    public void deleteUser(String userId){
        userController.deleteUser(userId);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
