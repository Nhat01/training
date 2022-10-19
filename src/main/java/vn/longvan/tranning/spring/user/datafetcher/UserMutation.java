package vn.longvan.tranning.spring.user.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import vn.longvan.tranning.spring.user.manager.UserManager;
import vn.longvan.tranning.spring.user.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@DgsComponent
public class UserMutation {
    @Autowired
    UserManager userManager;

    @DgsData(parentType = "Mutation", field = "addUser")
    public User addUser(DataFetchingEnvironment dataFetchingEnvironment) throws ParseException {
        DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        String name = dataFetchingEnvironment.getArgument("name");
        Date birthDay = simpleDateFormat.parse(dataFetchingEnvironment.getArgument("birthDay"));
        Date created = simpleDateFormat.parse(dataFetchingEnvironment.getArgument("created"));
        Date updated = simpleDateFormat.parse(dataFetchingEnvironment.getArgument("updated"));

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(name);
        user.setBirthDay(birthDay);
        user.setCreated(created);
        user.setUpdated(updated);

        userManager.createUser(user);

        return user;
    }

    @DgsData(parentType = "Mutation", field = "updateUserName")
    public User updateUserName(DataFetchingEnvironment dataFetchingEnvironment) throws ParseException {

        String id = dataFetchingEnvironment.getArgument("id");
        String name = dataFetchingEnvironment.getArgument("name");

        userManager.changeUserName(id, name);

        return userManager.getUser(id);
    }

    @DgsData(parentType = "Mutation", field = "deleteUser")
    public boolean deleteUser(DataFetchingEnvironment dataFetchingEnvironment) {
        String id = dataFetchingEnvironment.getArgument("id");

        userManager.deleteUser(id);

        return true;
    }
}
