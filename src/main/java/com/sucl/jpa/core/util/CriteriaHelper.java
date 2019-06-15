package com.sucl.jpa.core.util;

import com.sucl.jpa.core.orm.Condition;
import com.sucl.jpa.core.orm.jpa.JpaCondition;
import com.sucl.jpa.core.orm.jpa.JpaOrCondition;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import java.util.*;

/**
 * @author sucl
 * @date 2019/6/14
 */
public class CriteriaHelper {

    /**
     * 将条件中的别名提取出来
     * @param conditions
     * @param criteria
     * @return
     */
    public Object[] generateQueryExpression(Collection<Condition> conditions, Criteria criteria) {
        Object[] result = new Object[2];

        if ((conditions == null) || (conditions.size() == 0)) {
            result[0] = criteria;
            return result;
        }

        Set aliasSet = new HashSet();
        for (Condition condition : conditions) {
            if (condition == null)
                continue;
            criteria = processCondition(criteria, condition, aliasSet);
        }
        result[0] = criteria;
        result[1] = aliasSet;
        return result;
    }

    public Criteria processCondition(Criteria criteria, Condition condition, Set<String> aliasSet) {
        if ((condition instanceof JpaOrCondition)) {
            criteria = criteria.add(((JpaOrCondition) condition).generateExpression());
            criteria = processHibernateOrConditionAlias(criteria, (JpaOrCondition) condition, aliasSet);
        }
        if ((condition instanceof JpaCondition)) {
            processHibernateCondition(criteria, (JpaCondition) condition, aliasSet);
        }
        return criteria;
    }

    private Criteria processHibernateOrConditionAlias(Criteria criteria, JpaOrCondition condition, Set<String> aliasSet) {
        criteria = processHibernateOrConditionAlias(criteria, condition.getCondition1(), aliasSet);
        criteria = processHibernateOrConditionAlias(criteria, condition.getCondition2(), aliasSet);
        return criteria;
    }

    private Criteria processHibernateOrConditionAlias(Criteria criteria, Condition cond, Set<String> aliasSet) {
        if ((cond instanceof JpaCondition))
            criteria = processAlias(criteria, cond.getProperty(), aliasSet);
        else if ((cond instanceof JpaOrCondition)) {
            criteria = processHibernateOrConditionAlias(criteria, (JpaOrCondition) cond, aliasSet);
        }
        return criteria;
    }

    private Criteria processHibernateCondition(Criteria criteria, JpaCondition condition, Set<String> aliasSet) {
        Criterion conditionCriterion = condition.generateExpression();
        if (conditionCriterion != null) {
            criteria = processAlias(criteria, condition.getProperty(), aliasSet);
            criteria = criteria.add(conditionCriterion);
        }
        return criteria;
    }

    private Criteria processAlias(Criteria criteria, String propertyName, Set<String> aliasSet) {
        if (propertyName.indexOf(".") != -1) {
            String alias = propertyName.split("\\.")[0];
            if (!aliasSet.contains(alias)) {
                criteria = criteria.createAlias(alias, alias);
                aliasSet.add(alias);
            }
        }
        return criteria;
    }

    public DetachedCriteria getDetachedCriteria(Class<?> clazz) {
        return DetachedCriteria.forClass(clazz);
    }
}
