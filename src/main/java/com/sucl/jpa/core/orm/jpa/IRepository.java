package com.sucl.jpa.core.orm.jpa;

import com.sucl.jpa.core.orm.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * dao只用继承这里就像
 * @author sucl
 * @date 2019/6/14
 */
@NoRepositoryBean
public interface IRepository<T extends Domain> extends JpaRepository<T ,Serializable>,JpaSpecificationExecutor<T>,
        org.springframework.data.repository.Repository<T,Serializable> {
}
