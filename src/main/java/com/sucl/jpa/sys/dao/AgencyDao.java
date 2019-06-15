package com.sucl.jpa.sys.dao;

import com.sucl.jpa.sys.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
//@RepositoryRestResource(path="agency")
@Repository
public interface AgencyDao extends JpaRepository<Agency,Serializable>,JpaSpecificationExecutor<Agency>,
        org.springframework.data.repository.Repository<Agency,Serializable> {
}
