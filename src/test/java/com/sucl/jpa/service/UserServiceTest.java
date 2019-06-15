package com.sucl.jpa.service;

import com.sucl.jpa.Application;
import com.sucl.jpa.core.orm.Condition;
import com.sucl.jpa.core.orm.jpa.JpaCondition;
import com.sucl.jpa.sys.entity.User;
import com.sucl.jpa.sys.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 使用entityManager 去操作时，一直报错No transactional EntityManager available，无法理解
 * @author sucl
 * @date 2019/6/14
 */
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
//@ComponentScan("com.sucl.jpa")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager entityManager;

    @Before
    public void before(){
//        EntityTransaction t = entityManager.getTransaction();
    }

//    @Test
    public void save(){
        User user = new User();
        user.setUsername("aoom");
        userService.save(user);
    }

    /**
     * No transactional EntityManager available
     */
    @Test
    public void query(){
        List<Condition> conditions = new ArrayList<>();
        Condition cond = new JpaCondition("age","25");
        conditions.add(cond);
        List<User> users = userService.query(conditions);
        System.out.println(users);
    }
}
