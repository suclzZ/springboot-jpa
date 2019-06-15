package com.sucl.jpa.sys.service.impl;

import com.sucl.jpa.core.service.impl.BaseServiceImpl;
import com.sucl.jpa.sys.dao.RoleDao;
import com.sucl.jpa.sys.entity.Role;
import com.sucl.jpa.sys.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<RoleDao,Role> implements RoleService {
    @Override
    public Class<Role> getDomainClazz() {
        return Role.class;
    }
}
