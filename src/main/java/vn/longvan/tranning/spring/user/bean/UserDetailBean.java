package vn.longvan.tranning.spring.user.bean;

import com.sun.faces.action.RequestMapping;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import vn.longvan.tranning.spring.user.controller.UserController;
import vn.longvan.tranning.spring.user.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

@Component
@ViewScoped
public class UserDetailBean {
    @Autowired
    private UserController userController;

    private User user;

    public void onload() {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                String userId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
                if (userId != null) {
                    user = userController.getUserById(userId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if(this.user.getId() != null){
            userController.changeUserName(user.getId(), user.getName());
        }
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form");
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }


    public String redirectToHome() {
        return "index?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
