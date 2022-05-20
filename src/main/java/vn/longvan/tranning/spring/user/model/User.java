package vn.longvan.tranning.spring.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class User {
    @Id
    private String id;
    private String name;
    private Date birthDay;
    private Date created;
    private Date updated;
}
