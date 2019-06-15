package com.sucl.jpa.sys.web;

import com.sucl.jpa.core.web.BaseController;
import com.sucl.jpa.sys.entity.Role;
import com.sucl.jpa.sys.service.RoleService;
import com.sucl.jpa.core.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleService,Role> {

}
