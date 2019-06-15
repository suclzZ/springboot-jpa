package com.sucl.jpa.sys.service.impl;

import com.sucl.jpa.sys.dao.AgencyDao;
import com.sucl.jpa.sys.entity.Agency;
import com.sucl.jpa.sys.service.AgencyService;
import com.sucl.jpa.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class AgencyServiceImpl extends BaseServiceImpl<AgencyDao,Agency> implements AgencyService {
    @Override
    public Class<Agency> getDomainClazz() {
        return Agency.class;
    }
}
