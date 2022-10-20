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


@ManagedBean(name = "userBean")
@SessionScoped
@Component
public class UserBean implements Serializable {
    @Autowired
    UserController userController;

    private User user = new User();

    private List<User> users = new ArrayList<>();

    /**
     * Khi người dùng load trang web hoặc ấn refresh
     * index -> bean -> controller
     * @param
     * @return
     */
    public void loadData(){
        users = userController.listAllUsers();
    }

    /**
     * Khi người ấn vào thêm người dùng tạo đối tượng
     * người dùng mới và chuyển hướng đến trang add User
     * @param
     * @return
     */
    public String addUser(){
        user = new User();
        return "addUser?faces-redirect=true";
    }

    public String loadUserDetail(String userId) {
        user = userController.getUserById(userId);
        return "userDetail?faces-redirect=true";
    }

    /**
     * Khi người dùng tạo user
     * khi thành công sẽ được chuyển hướng về trang user detail
     * @param
     * @return
     */
    public String createUser(){
        userController.createUser(user);
        return "userDetail";
    }

    /**
     * Khi người dùng sửa tên
     * khi thành công sẽ được chuyển hướng về trang user detail
     * @param
     * @return userDetail page
     */
    public String changeNameUser(){
        userController.changeNameUser(user.getId(), user.getName());
        return "userDetail?faces-redirect=true";
    }

    /**
     * Người dùng xóa user
     * @param userId
     * @return
     */
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
