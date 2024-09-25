package vn.longvan.tranning.user.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import vn.longvan.tranning.spring.user.exception.UserAlreadyExistException;
import vn.longvan.tranning.spring.user.exception.UserNotExistException;
import vn.longvan.tranning.spring.user.model.User;
import vn.longvan.tranning.spring.user.manager.UserManager;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTrainingApplication.class)
@AutoConfigureDataMongo
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestUserManager {

    @Autowired
    UserManager userManager;

    User createUser;

    @Test
    @Order(1)
    public void setup() {

    }
    @Test
    @Order(2)
    public void testCreateUser() {
        /**
         *  Trình tự test
         *   1. Create user.
         *   2. Get user kiểm tra thông tin trả về.
         *   3. Test tao user đã tồn tại
         */
        createUser = new User();
        createUser.setId(UUID.randomUUID().toString());
        createUser.setName("Test User");
        createUser.setBirthDay(new Date());
        try {
            userManager.createUser(createUser);
            User user = userManager.getUser(createUser.getId());
            assertEquals(user.getName(), createUser.getName());
        } catch (Exception e){

        }
        assertThrows(UserAlreadyExistException.class, () -> {
            userManager.createUser(createUser);
        });
    }

    @Test
    @Order(3)
    public void testUpdateUserName() {
        /**
         *  Trình tự test
         *  1. Change user name.
         *  2. Get user kiểm tra tên user
         *  3. Change user name không tồn tại
         */
        createUser = new User();
        createUser.setId(UUID.randomUUID().toString());
        createUser.setName("Test User");
        createUser.setBirthDay(new Date());
        try {
            userManager.changeUserName(createUser.getId(), "New Name");
            User user = userManager.getUser(createUser.getId());
            assertEquals(user.getName(), createUser.getName());
        } catch (Exception e){
            assertThrows(UserNotExistException.class, () -> {
                userManager.changeUserName(createUser.getId(), "New Name");
            });
        }

    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        /**
         *  Trình tự test
         *  1. Thực hiện xóa user
         *  2. Get user kiểm tra tên user có bằng null
         *  3. Xóa user không tồn tại
         */
        createUser = new User();
        createUser.setId("90aecc90-865e-4362-8427-7b7ab2a63012");
        try {
            userManager.deleteUser(createUser.getId());
        } catch (Exception e){
            assertThrows(UserNotExistException.class, () -> {
                userManager.deleteUser(createUser.getId());
            });
        }
    }
}
