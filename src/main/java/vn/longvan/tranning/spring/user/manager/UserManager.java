package vn.longvan.tranning.spring.user.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import vn.longvan.tranning.spring.user.exception.UserAlreadyExistException;
import vn.longvan.tranning.spring.user.exception.UserNotExistException;
import vn.longvan.tranning.spring.user.model.User;

import java.util.List;

public class UserManager {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * Find user by name.
     *
     * @param name
     * @return
     */
    public List<User> findByName(String name) {
        return mongoTemplate.find(Query.query(Criteria.where("name").is(name)), User.class);
    }

    /**
     * Get user by userid
     *
     * @param id
     * @return null nếu user không tồn tại
     */
    public User getUser(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), User.class);
    }

    /**
     * Create new user.
     * Kiểm tra nếu user đã tồn tại thì trả về exception  UserAlreadyExits
     * Chú ý kiểm userid chưa tồn tại mới cho insert
     * Cần tìm hiểu các vấn đề sau.
     * - mongo upsert
     *
     * @param user
     */
    public void createUser(User user) throws UserAlreadyExistException {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").is(user.getId())), User.class)) {
            throw new UserAlreadyExistException(USER_EXIST, user);
        }
        mongoTemplate.save(user, "user");
    }

    /**
     * Change user name
     * - Nếu user không tồn tại thì trả vể UserNotExitsException
     *
     * @param userId
     * @param newName
     */
    public void changeUserName(String userId, String newName) throws UserNotExistException {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("id").is(userId)), User.class);

        if (user == null) {
            throw new UserNotExistException(USER_NOT_EXIST, userId);
        }

        user.setName(newName);
        mongoTemplate.save(user);
    }

    public static String USER_EXIST = "User đã tồn tại";
    public static String USER_NOT_EXIST = "User không tồn tại";

}
