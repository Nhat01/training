package vn.longvan.tranning.spring.user.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import vn.longvan.tranning.spring.user.controller.UserController;
import vn.longvan.tranning.spring.user.model.User;

@DgsComponent
public class UserMutation {
    @Autowired
    private UserController userController;

    @DgsMutation
    public void createUser(@InputArgument("user") User user) {
        userController.createUser(user);
    }

    @DgsMutation
    public void updateUser(
            @InputArgument("userId") String userId,
            @InputArgument("newName") String newName) {
        userController.changeUserName(userId, newName);
    }

    @DgsMutation
    public void deleteUser(
            @InputArgument("userId") String userId) {
        userController.deleteUser(userId);
    }
}
