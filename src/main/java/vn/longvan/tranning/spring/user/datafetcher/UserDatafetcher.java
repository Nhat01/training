package vn.longvan.tranning.spring.user.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.primefaces.model.FilterMeta;
import org.springframework.beans.factory.annotation.Autowired;
import vn.longvan.tranning.spring.user.controller.UserController;
import vn.longvan.tranning.spring.user.model.User;

import java.util.List;
import java.util.Map;

@DgsComponent
public class UserDatafetcher {
    @Autowired
    private UserController userController;

    @DgsQuery
    public List<User> getAllUsers(){
        return userController.getAllUser();
    }

    @DgsQuery
    public User getUserById(@InputArgument String id) {
        return userController.getUserById(id);
    }

    @DgsQuery
    public List<User> getUserByName(@InputArgument String name) {
        return userController.getUserByName(name);
    }


}
