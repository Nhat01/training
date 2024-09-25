package vn.longvan.tranning.spring.user.bean;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.longvan.tranning.spring.user.controller.UserController;
import vn.longvan.tranning.spring.user.model.User;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@Component
@ViewScoped
public class UserListBean {
    @Autowired
    private UserController userController;

    private LazyDataModel<User> lazyModel;

    private User user;

    public void onload() {
        lazyModel = new LazyUserDataModel(userController);
    }
    public void openNew(){
        this.user = new User();
    }

    public void saveUser(){
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Name cannot be empty.");
            PrimeFaces.current().ajax().update("growl");
            return;
        }
        if (user.getBirthDay() == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "BirthDay cannot be empty.");
            PrimeFaces.current().ajax().update("growl");
            return;
        }
        if(this.user.getId() == null){
            userController.createUser(user);
        }
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:userTable");
    }

    public void deleteUser(){
        if(this.user.getId() != null){
            userController.deleteUser(user.getId());
        }
        PrimeFaces.current().executeScript("PF('deleteUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:userTable");
    }

    public void redirectDetail(){
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("detail.xhtml?id=" + user.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }


    public LazyDataModel<User> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<User> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
