package com.sucl.jpa.core.db;

import com.sucl.jpa.core.service.DbTypeService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 不同数据库对应的数据类型不同
 * @author sucl
 * @date 2019/6/13
 */
@Component
public class DefaultDataTypeConverter implements DataTypeConverter ,ApplicationContextAware {
    private String defaultDb = "mysql";
    @Autowired(required = false)
    private DbTypeService dbTypeService;

    private List<DataTypeConverter> dataTypeConverters;

    @Override
    public boolean support(DbType dbType) {
        return false;
    }

    @Override
    public String getDatatypeAndLength(TableCreateHelper.DataType dataType, String length) {
        DbType dbType = null;
        if(dbTypeService!=null){
            dbType = dbTypeService.getDbType();
        }
        if(dbType==null){
            dbType = DbType.valueOf(defaultDb.toUpperCase());
        }
        if(dbType!=null){
            if(dataTypeConverters!=null){
                for(DataTypeConverter dataTypeConverter : dataTypeConverters){
                    if(dataTypeConverter.support(dbType)){
                        return dataTypeConverter.getDatatypeAndLength(dataType,length);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, DataTypeConverter> dataTypeConverterMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, DataTypeConverter.class, true, false);
        if(MapUtils.isNotEmpty(dataTypeConverterMap)){
            this.dataTypeConverters = new ArrayList<>(dataTypeConverterMap.values());
        }
    }
}
