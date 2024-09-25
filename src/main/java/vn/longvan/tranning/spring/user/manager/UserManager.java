package vn.longvan.tranning.spring.user.manager;

import com.mongodb.client.result.DeleteResult;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import vn.longvan.tranning.spring.user.exception.UserAlreadyExistException;
import vn.longvan.tranning.spring.user.exception.UserNotExistException;
import vn.longvan.tranning.spring.user.model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class UserManager {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * Get all users
     *
     * @return list user và null nếu user không tồn tại
     */
    public List<User> getAllUser() {
        return mongoTemplate.findAll(User.class);
    }

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
        User oldUser = getUser(user.getId());
        if (oldUser != null) throw new UserAlreadyExistException(USER_EXIST, oldUser);
        user.setCreated(new Date());
        mongoTemplate.insert(user);
    }

    /**
     * Change user name
     * - Nếu user không tồn tại thì trả vể UserNotExitsException
     *
     * @param userId
     * @param newName
     */
    public void changeUserName(String userId, String newName) throws UserNotExistException {
        User user = getUser(userId);
        if (user == null) throw new UserNotExistException(USER_NOT_EXIST, userId);
        user.setName(newName);
        user.setUpdated(new Date());
        mongoTemplate.save(user);
    }

    /**
     * Delete
     * Kiểm tra nếu user không tồn tại thì trả về exception UserNotExistException
     *
     * @param userId
     */
    public void deleteUser(String userId) throws UserNotExistException {
        User user = getUser(userId);
        if (user == null) throw new UserNotExistException(USER_NOT_EXIST, userId);
        mongoTemplate.remove(user);
    }

    public List<User> getUsers(int offset, int pageSize, Map<String, FilterMeta> filter, Map<String, SortMeta> sort) {
        Query query = new Query();
        filterUser(query, filter);
        sort.forEach((key, value)-> {
            if ("ASCENDING".equalsIgnoreCase(value.getOrder().toString())) {
                query.with(Sort.by(Sort.Direction.ASC, key));
            } else if("DESCENDING".equalsIgnoreCase(value.getOrder().toString())) {
                query.with(Sort.by(Sort.Direction.DESC, key));
            }
        });
        query.skip(offset).limit(pageSize);

        return mongoTemplate.find(query, User.class);
    }

    public int countUsers(Map<String, FilterMeta> filterBy) {
        Query query = new Query();
        filterUser(query, filterBy);
        return (int) mongoTemplate.count(query, User.class);
    }

    private void filterUser(Query query, Map<String, FilterMeta> filterBy) {
        filterBy.forEach((key, value) -> {
            if (key.equals("birthDay")) {
                List<LocalDate> dateRange = (List<LocalDate>) value.getFilterValue();
                LocalDate startDateLocal = dateRange.get(0);
                LocalDate endDateLocal = dateRange.get(1);

                Date startDate = java.sql.Date.valueOf(startDateLocal);
                Date endDate = java.sql.Date.valueOf(endDateLocal);

                query.addCriteria(Criteria.where("birthDay").gte(startDate).lte(endDate));
            } else {
                String safePattern = Pattern.quote(value.getFilterValue().toString());
                query.addCriteria(Criteria.where(key).regex(".*" + safePattern + ".*", "i"));
            }
        });
    }

    public static String USER_EXIST = "User đã tồn tại";
    public static String USER_NOT_EXIST = "User không tồn tại";

}
