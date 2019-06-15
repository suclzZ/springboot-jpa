package com.sucl.jpa.core.db;

/**
 * @author sucl
 * @date 2019/6/13
 */
public interface DataTypeConverter {

    boolean support(DbType dbType);

    String getDatatypeAndLength(TableCreateHelper.DataType dataType,String length);

}
