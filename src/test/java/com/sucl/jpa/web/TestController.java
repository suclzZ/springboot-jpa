package com.sucl.jpa.web;

import com.sucl.jpa.core.orm.Condition;
import com.sucl.jpa.core.orm.jpa.JpaCondition;
import com.sucl.jpa.sys.entity.User;
import com.sucl.jpa.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/6/15
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping
    public void get(){
        List<Condition> conditions = new ArrayList<>();
        Condition cond = new JpaCondition("age","25");
        Condition cond2 = new JpaCondition("agency.agencyId","0103");
        conditions.add(cond); conditions.add(cond2);
        List<User> users = userService.query(conditions);
        System.out.println(users);
    }
}
