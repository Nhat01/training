package vn.longvan.tranning.spring.user.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import vn.longvan.tranning.spring.user.manager.UserManager;
import vn.longvan.tranning.spring.user.model.User;

import java.util.List;

@DgsComponent
public class UserDatafetcher {
    @Autowired
    UserManager userManager;

    @DgsQuery
    public List<User> users(){
        return userManager.getAllUser();
    }

    @DgsQuery
    public User user(@InputArgument("id") String id){
        return userManager.getUser(id);
    }

    @DgsQuery
    public List<User> username(@InputArgument("name") String name){
        return userManager.findByName(name);
    }


}
