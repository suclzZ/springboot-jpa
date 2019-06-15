package com.sucl.jpa.core.service;

import com.sucl.jpa.core.orm.Condition;
import com.sucl.jpa.core.orm.Order;
import com.sucl.jpa.core.orm.ext.Pager;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * 基础服务
 * @author sucl
 * @date 2019/4/1
 */
public interface BaseService<R ,T> {

    /**
     * 获取实体class
     * @return
     */
    default Class<T> getDomainClazz(){
        throw new RuntimeException("No entity type is defined.");
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 根据多个主键查询
     * @param ids
     * @return
     */
    List<T> getByIds(Collection ids);

    /**
     * 根据唯一属性查询唯一对象
     * @param property
     * @param value
     * @return
     */
    T getOne(String property,Object value);

    /**
     * 根据主键查询，可查询出延迟属性
     * @param id
     * @param props
     * @return
     */
    T getInitializeObject(Serializable id, String[] props);

    /**
     * 根据条件查询,仅支持=查询
     * @param conditions
     * @return
     */
    List<T> getAllByCondition(Collection<Condition> conditions);

    /**
     * 根据条件查询和排序
     * @param conditions
     * @param sort
     * @return
     */
    List<T> getAllByCondition(Collection<Condition> conditions,Sort sort);

    /**
     * 根据对象查询
     * @param t
     * @return
     */
    List<T> getAll(T t);

    /**
     * 根据对象查询和排序
     * @param t
     * @param sort
     * @return
     */
    public List<T> getAll(T t,Sort sort);

    /**
     * 分页条件排序查询
     * @param pager
     * @param conditions
     * @param orders
     * @return
     */
    Pager<T> getPagerWithCondOrder(Pager pager, Collection<Condition> conditions, Collection<Order> orders);

    /**
     * 分页条件排序查询
     * @param pager
     * @param conditions
     * @param sort
     * @return
     */
    Pager<T> getPagerWithCondSort(Pager pager, Collection<Condition> conditions, Sort sort);

    /**
     * 保存有主键新增；否则修改
     * 有主键没有对象，异常
     * @param t
     * @return
     */
    T save(T t);

    /**
     * 批量保存/修改
     * @param ts
     */
    void saveBatch(Collection<T> ts);

    /**
     * 根据主键修改
     * @param t
     * @return
     */
    T updateById(T t);

    /**
     * 同save
     * @param t
     * @return
     */
    T saveOrUpdate(T t);

    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(Serializable id);

    /**
     * 根据条件删除
     * @param property
     * @param value
     */
    void delete(String property,Object value);

    /**
     * 根据对象删除
     * @param t
     */
    void delete(T t);

    /**
     * 根据条件批量删除
     * @param ts
     */
    void deleteAll(Collection<T> ts);

    /**
     * 根据主键判断对象是否存在
     * @param id
     * @return
     */
    boolean exist(Serializable id);

    /**
     * 根据对象判断对象是否存在
     * @param t
     * @return
     */
    boolean exist(T t);

    /**
     * 根据属性判断对象是否存在
     * @param prop
     * @param value
     * @return
     */
    boolean exist(String prop,Object value);

    /**
     * 根据条件查询，支持各种关系查询
     * @param conditions
     * @return
     */
    List<T> query(Collection<Condition> conditions);

    /**
     *根据条件分页查询，支持各种关系查询
     * @param conditions
     * @param pager
     * @return
     */
    Pager query(Collection<Condition> conditions,Pager pager);

    /**
     * 直接写sql进行分页
     * @param jpql
     * @param pager
     * @return
     */
    Pager query(String jpql,Pager pager);

    /**
     * 根据HQL/JPQL查询
     * @param hql
     * @param callable
     */
    void query(String hql,Consumer callable);

    /**
     * 根据sql查询
     * @param sql
     * @param callable
     */
    void nativeQuery(String sql,Consumer callable);

    /**
     * 根据sql修改
     * @param sql
     */
    void nativeUpdate(String sql);
}
