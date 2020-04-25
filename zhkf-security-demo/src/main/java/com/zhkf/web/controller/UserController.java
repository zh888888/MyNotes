package com.zhkf.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zhkf.dto.User;
import com.zhkf.dto.UserQueryCondition;
import com.zhkf.exception.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

import org.springframework.data.domain.Pageable;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    //获取用户认证信息
    @GetMapping("/me")
    public Object getMe(@AuthenticationPrincipal UserDetails user){

      //  System.out.println(SecurityContextHolder.getContext().getAuthentication());
       // return SecurityContextHolder.getContext().getAuthentication();
        return user;
    }

    @PostMapping
        public User create(@Valid @RequestBody User user/*, BindingResult errors*/) {

     /*   if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }*/
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBrityday());
        user.setId("1");
        return user;
    }

    // @RequestMapping(value="/user",method = RequestMethod.GET)
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value="用户查询服务")           //swagger-ui中返回方法名
    public List<User> getUser(UserQueryCondition condition, @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {
        //利用Java的反射查询
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    // @RequestMapping(value="/user/{id:\\d+}",method=RequestMethod.GET)
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam("用户id") @PathVariable String id) {
       // throw new UserNotExistException(id);
        System.out.println("进入getInfo()服务");
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {

       if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
               // FieldError fieldError = (FieldError)error;
                 //String message = fieldError.getField() +" "+
                 //error.getDefaultMessage();
                 //System.out.println(message);
                System.out.println(error.getDefaultMessage());
            });
        }

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBrityday());

        user.setId("1");
        return user;
    }

    //删除服务
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println(id);
    }
}

