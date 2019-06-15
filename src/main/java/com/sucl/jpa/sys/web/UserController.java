package com.sucl.jpa.sys.web;

import com.sucl.jpa.core.web.BaseController;
import com.sucl.jpa.sys.entity.User;
import com.sucl.jpa.sys.service.UserService;
import com.sucl.jpa.core.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserService,User> {

}
