package com.sucl.jpa.sys.service.impl;

import com.sucl.jpa.core.service.impl.BaseServiceImpl;
import com.sucl.jpa.sys.dao.UserDao;
import com.sucl.jpa.sys.entity.User;
import com.sucl.jpa.sys.service.UserService;
import com.sucl.jpa.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserDao,User> implements UserService {

    @Override
    public Class<User> getDomainClazz() {
        return User.class;
    }

    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    }
