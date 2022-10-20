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
        createUser = new User();
        createUser.setId(UUID.randomUUID().toString());
    }

    @Test
    @Order(2)
    public void testCreateUser() {
        /**
         *  Trình tự test
         *   1. Create user.
         *   2. Get user kiểm tra thông tin trả về.
         *   3. Test tao user đã tồn tại
         *
         */
        // 1. Create user.
        createUser.setName("Vo Hoai Thuong");
        createUser.setBirthDay(new Date());
        createUser.setCreated(new Date());
        createUser.setUpdated(new Date());

        userManager.createUser(createUser);

        //2. Get user kiểm tra thông tin trả về.
        User userTest = userManager.getUser(createUser.getId());
        Assertions.assertEquals(createUser.getId(), userTest.getId());
        Assertions.assertEquals(createUser.getName(), userTest.getName());
        Assertions.assertEquals(createUser.getBirthDay(), userTest.getBirthDay());
        Assertions.assertEquals(createUser.getCreated(), userTest.getCreated());
        Assertions.assertEquals(createUser.getUpdated(), userTest.getUpdated());

        // 3. Test tao user đã tồn tại
        UserAlreadyExistException thrown = Assertions.assertThrows(
                UserAlreadyExistException.class,
                () -> {
                    userManager.createUser(createUser);
                }
        );

        Assertions.assertEquals(UserManager.USER_EXIST, thrown.getMessage());

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
        String userId = "6350a639853eb67582bc08e8";
        String userIdNotExist = "0";
        String newName = "Change Vo Hoai Thuong";

        userManager.changeUserName(userId, newName);

        // 2. Get user kiểm tra tên user
        User userTest = userManager.getUser(userId);

        Assertions.assertEquals(newName, userTest.getName());

        // 3. Change user name không tồn tại
        UserNotExistException thrown = Assertions.assertThrows(
                UserNotExistException.class,
                () -> {
                    userManager.changeUserName(userIdNotExist, newName);
                }
        );
        Assertions.assertEquals(UserManager.USER_NOT_EXIST, thrown.getMessage());

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
        String userIdNotExist = "0";

        userManager.deleteUser(createUser.getId());
        Assertions.assertNull(userManager.getUser(createUser.getId()));

        UserNotExistException thrown = Assertions.assertThrows(
                UserNotExistException.class,
                () -> {
                    userManager.deleteUser(userIdNotExist);
                }
        );
        Assertions.assertEquals(UserManager.USER_NOT_EXIST, thrown.getMessage());
    }
}
