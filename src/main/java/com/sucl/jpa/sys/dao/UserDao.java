package com.sucl.jpa.sys.dao;

import com.sucl.jpa.sys.entity.User;
import com.sucl.jpa.sys.entity.User;
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
public interface UserDao extends JpaRepository<User,Serializable>,JpaSpecificationExecutor<User>,
        org.springframework.data.repository.Repository<User,Serializable>{

    User findByUsername(String username);

//    @Modifying
//    @Query(value = "update sys_user set age = ?1 where username = ?2 ",nativeQuery = true)
//    void updateTest(String age,String name);
}
