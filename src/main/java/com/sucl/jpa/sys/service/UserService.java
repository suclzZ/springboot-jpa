package com.sucl.jpa.sys.service;

import com.sucl.jpa.core.service.BaseService;
import com.sucl.jpa.sys.dao.UserDao;
import com.sucl.jpa.sys.entity.User;
import com.sucl.jpa.sys.entity.User;

/**
 * @author sucl
 * @date 2019/4/2
 */
public interface UserService extends BaseService<UserDao,User> {

    User findByUsername(String username);

}
