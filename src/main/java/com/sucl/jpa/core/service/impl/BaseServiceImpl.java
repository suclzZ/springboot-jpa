package com.sucl.jpa.core.service.impl;

import com.sucl.jpa.core.orm.Condition;
import com.sucl.jpa.core.orm.Order;
import com.sucl.jpa.core.orm.ext.Pager;
import com.sucl.jpa.core.rem.BusException;
import com.sucl.jpa.core.rem.ExceptionUtils;
import com.sucl.jpa.core.service.BaseService;
import com.sucl.jpa.core.util.ConditionHelper;
import com.sucl.jpa.core.util.CriteriaHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * 方便之后对泛型R的使用，放弃Repository类型注入，改为JpaRepository,否则需要做各种类型校验与转换
 *
 * @author sucl
 * @date 2019/4/1
 */
public abstract class BaseServiceImpl<R extends Repository<T,? extends Serializable>,T> implements BaseService<R,T> {

    /**
     * 原始dao
     */
    protected R dao;
    /**
     * 通过examle查询
     */
    protected JpaRepository<T,Serializable> repository;
    /**
     * 通过Specification查询
     */
    protected JpaSpecificationExecutor<T> specificationExecutor;

    @Resource
    public void setRepository(R r) {
        this.dao = r;
        if(r instanceof JpaRepository){
            this.repository = (JpaRepository<T, Serializable>) r;
        }
        if(r instanceof JpaSpecificationExecutor){
            this.specificationExecutor = (JpaSpecificationExecutor<T>) r;
        }
    }

    /**
     * findOne
     * getOne  代理，你会发现 经常查询出来是个null
     * @param id
     * @return
     */
    @Override
    public T getById(Serializable id) {
        return repository.findOne(id);
    }

    @Override
    public List<T> getByIds(Collection ids){
        return repository.findAll(ids);
    }

    @Override
    public T getOne(String property, Object value) {
        return repository.findOne(ConditionHelper.buildExample(getDomainClazz(),property,value));
    }

    @Override
    public T getInitializeObject(Serializable id, String[] props) {
        T t = repository.findOne(id);
        initializeObjectCollections(t, props);
        return t;
    }

    private void initializeObjectCollections(T initializeObject, String[] props) {
        if(props!=null){
            for(String prop : props){
                initializeObjectCollection(initializeObject, prop);
            }
        }
    }

    private void initializeObjectCollection(T initializeObject, String prop) {
        try {
            if(StringUtils.isNotEmpty(prop)){
                Object obj = PropertyUtils.getProperty(initializeObject, prop);
                Hibernate.initialize(obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAllByCondition(Collection<Condition> conditions) {
        return specificationExecutor.findAll(ConditionHelper.buildSpecification(conditions));
    }

    @Override
    public List<T> getAllByCondition(Collection<Condition> conditions,Sort sort) {
        return specificationExecutor.findAll(ConditionHelper.buildSpecification(conditions),sort);
    }

    @Override
    public List<T> getAll(T t) {
        if(t==null){
            return repository.findAll();
        }
        return repository.findAll(Example.of(t));
    }

    @Override
    public List<T> getAll(T t,Sort sort) {
        if(t==null){
            return repository.findAll(null,sort);
        }
        return repository.findAll(Example.of(t),sort);
    }

    @Override
    public Pager<T> getPagerWithCondOrder(Pager pager, Collection<Condition> conditions, Collection<Order> orders) {
        Pageable pageable = new PageRequest(pager.getPageIndex()-1,pager.getPageSize(),ConditionHelper.buildSort(orders));
        buildPager(pager,conditions,pageable);
        return pager;
    }

    @Override
    public Pager<T> getPagerWithCondSort(Pager pager, Collection<Condition> conditions, Sort sort) {
        Pageable pageable = new PageRequest(pager.getPageIndex()-1,pager.getPageSize(),sort);
        buildPager(pager,conditions,pageable);
        return pager;
    }

    private void buildPager(Pager pager,Collection<Condition> conditions,Pageable pageable){
        Page<T> page = specificationExecutor.findAll(ConditionHelper.buildSpecification(conditions), pageable);
        pager.setMaxPage(page.getTotalPages());
        pager.setTotal( (int)page.getTotalElements());
        pager.setResult(page.getContent());
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public void saveBatch(Collection<T> ts) {
        repository.save(ts);
    }

    @Override
    public T updateById(T t) {
        return repository.save(t);
    }

    @Override
    public T saveOrUpdate(T t) {
        return repository.save(t);
    }

    @Override
    public void deleteById(Serializable id) {
        repository.delete(id);
    }

    @Override
    public void delete(String property, Object value) {
        Class<T> clazz = getDomainClazz();
        T t = BeanUtils.instantiate(clazz);
        boolean delete = true;
        try {
            PropertyUtils.setProperty(t,property,value);
        } catch (Exception e) {
            delete = false;
            throw new BusException(String.format("failed to set property[%s] for [%s], failed to delete.",property,clazz));
        }
        if(delete)
            repository.delete(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteAll(Collection<T> ts) {
        repository.deleteInBatch(ts);
    }

    @Override
    public boolean exist(Serializable id) {
        return repository.exists(id);
    }

    @Override
    public boolean exist(T t) {
        return repository.exists(Example.of(t));
    }

    @Override
    public boolean exist(String prop,Object value) {
        return getOne(prop,value)!=null;
    }

    protected EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 该查询比较复杂，设计各种组合嵌套
     * 但不支持关联查询，如有需要参考上面
     * @param conditions
     * @return
     */
    public List<T> query(Collection<Condition> conditions) {
        CriteriaHelper criteriaHelper = new CriteriaHelper();
        DetachedCriteria detachedCriteria = criteriaHelper.getDetachedCriteria(getDomainClazz());

        Session session = entityManager.unwrap(org.hibernate.Session.class);
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);//session

        criteriaHelper.generateQueryExpression(conditions, criteria);//1
        try {
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusException("query exception:", ExceptionUtils.getRootCause(e));
        }
//
    }

    public Pager query(Collection<Condition> conditions,Pager pager){
        CriteriaHelper criteriaHelper = new CriteriaHelper();
        DetachedCriteria detachedCriteria = criteriaHelper.getDetachedCriteria(getDomainClazz());

        Session session = entityManager.unwrap(org.hibernate.Session.class);
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);//session

        criteriaHelper.generateQueryExpression(conditions, criteria);
        int total = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult(pager.getPageSart()).setMaxResults(pager.getPageSize());
        List result = criteria.list();
        pager.setResult(result);
        pager.setTotal(total);
        return pager;
    }

    public Pager query(String jpql,Pager pager){
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(pager.getPageIndex());
        query.setMaxResults(pager.getPageSize());
        pager.setResult(query.getResultList());

        Query totalQuery = entityManager.createQuery("select count(1) from ("+jpql+")");
        Object totalRes = totalQuery.getSingleResult();
        int total = NumberUtils.toInt(totalRes.toString());
        pager.setTotal(total);
        return pager;
    }

    public void query(String hql,Consumer callable){
        Query query = entityManager.createQuery(hql);
        if(callable!=null){
            callable.accept(query.getResultList());
        }
    }

    public void nativeQuery(String sql,Consumer callable){
        Query query = entityManager.createNativeQuery(sql);
        if(callable!=null){
            callable.accept(query.getResultList());
        }
    }

    @Transactional
    public void nativeUpdate(String sql){
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}
