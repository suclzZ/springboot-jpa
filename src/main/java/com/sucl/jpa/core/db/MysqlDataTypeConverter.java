package com.sucl.jpa.core.db;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author sucl
 * @date 2019/6/13
 */
@Component
public class MysqlDataTypeConverter implements DataTypeConverter{
    @Override
    public boolean support(DbType dbType) {
        return dbType == DbType.MYSQL;
    }

    @Override
    public String getDatatypeAndLength(TableCreateHelper.DataType dataType, String length) {
        if(MysqlDatatype.support(dataType)){
            MysqlDatatype dt = MysqlDatatype.valueOf(dataType.name());
            return dt.getDatatypeAndLength(dataType,length);
        }
        return null;
    }

    public enum MysqlDatatype{
        INTEGER("INTEGER"),
        DOUBLE("DECIMAL(%s,%s)"),
        STRING("VARCHAR(%s)"),
        BOOLEAN("CHAR(1)"),
        DATE("DATE"),
        TIMESTAMP("TIMESTAMP");

        private String format;

        MysqlDatatype(String format){
            this.format = format;
        }

        public static boolean support(TableCreateHelper.DataType dataType) {
            return valueOf(dataType.name())!=null;
        }

        public String getDatatypeAndLength(TableCreateHelper.DataType dataType,String length){
            String[] lengths=null;
            if(StringUtils.isNotEmpty(length)){
                lengths = length.split(",");
            }
            return String.format(format,lengths);
        }
    }
}
