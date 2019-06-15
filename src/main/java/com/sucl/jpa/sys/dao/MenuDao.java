package com.sucl.jpa.sys.dao;

import com.sucl.jpa.sys.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
//@RepositoryRestResource(path="user")
@Repository
public interface MenuDao extends JpaRepository<Menu,Serializable>,JpaSpecificationExecutor<Menu>,
        org.springframework.data.repository.Repository<Menu,Serializable>{

}
