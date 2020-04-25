package com.zhkf.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.zhkf.validator.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

public class User {

    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView{}
    private String id;

    @MyConstraint(message = "这是一个测试注解")
    private String username;

    @NotBlank (message="密码不能为空！")          //不为空
    private String password;

    @Past(message="生日必须是历史时间！")           //过去的时间
    private Date brityday;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBrityday() {
        return brityday;
    }

    public void setBrityday(Date brityday) {
        this.brityday = brityday;
    }
}
