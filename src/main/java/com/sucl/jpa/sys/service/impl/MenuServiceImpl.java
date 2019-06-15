package com.sucl.jpa.sys.service.impl;

import com.sucl.jpa.core.service.impl.BaseServiceImpl;
import com.sucl.jpa.sys.dao.MenuDao;
import com.sucl.jpa.sys.entity.Menu;
import com.sucl.jpa.sys.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<MenuDao,Menu> implements MenuService {

    @Override
    public Class<Menu> getDomainClazz() {
        return Menu.class;
    }

}
