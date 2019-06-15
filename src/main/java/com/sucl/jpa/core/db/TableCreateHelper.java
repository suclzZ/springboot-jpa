package com.sucl.jpa.core.db;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Jan数据库表
 * @author sucl
 * @date 2019/6/13
 */
@Data
@Slf4j
public class TableCreateHelper {
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String DEFAULT_ID = "ID";

    private DataTypeConverter dataTypeConverter;
    private DefaultValueConverter defaultValueConverter;

    public String createSql(String tableName , List<? extends Column> columns){
        return buildSql(tableName,columns);
    }

    private String buildSql(String tableName,List<? extends Column> columns){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ").append(tableName).append(SPACE).append(LEFT_BRACKET);
        if(CollectionUtils.isNotEmpty(columns)){
            for(int i=0;i<columns.size();i++){
                buildColumn(sqlBuilder,columns.get(i));
                if(i<columns.size()-1){
                    sqlBuilder.append(COMMA);
                }
            }
        }else{
            sqlBuilder.append("ID varchar(36) PRIMARY KEY NOT NULL");
        }
        sqlBuilder.append(RIGHT_BRACKET);
        return sqlBuilder.toString();
    }

    private void buildColumn(StringBuilder sqlBuilder, Column column) {
        validate(column);
        sqlBuilder.append(column.getFieldName()).append(SPACE).append(datatypeAndLength(column.getDataType(),column.getLength()));
        if(column.isPk()){
            sqlBuilder.append(SPACE).append("PRIMARY KEY");
        }
        if(column.isNulldisable()){
            sqlBuilder.append(SPACE).append("NOT NULL");
        }
        if(column.getDefaultValue()!=null){
            sqlBuilder.append(SPACE).append("DEFAULT"+defaultValue(column.getDataType(),column.getDefaultValue()));
        }
    }

    private void validate(Column column) {
        String length = column.getLength(),reg = "(^\\d+[,]?\\d+$)|(^\\d+)";//N,N ;N
        if(length!=null){
            if(!Pattern.compile(reg).matcher(length).matches()){
                throw new RuntimeException(String.format( "length:[%s] is not valid!",length));
            }
        }
    }

    private String defaultValue(DataType dataType,String defaultValue) {
        if(defaultValueConverter!=null){
            return defaultValueConverter.defaultValue(dataType,defaultValue);
        }
        return "'"+defaultValue+"'";
    }

    private String datatypeAndLength(DataType dataType, String length) {
        String datatypeAndLength = null;
        if(dataTypeConverter!=null){
            datatypeAndLength = dataTypeConverter.getDatatypeAndLength(dataType,length);
        }
        if(datatypeAndLength ==null){
            log.warn("datatypeAndLength is null!");
            if(length==null){
                length = "36";
            }
            return  String.format("varchar(%s)",length.split(","));
        }else{
            return datatypeAndLength;
        }
    }

    public interface Column{
        String getFieldName();

        DataType getDataType();

        String getLength();

        boolean isPk();

        boolean isNulldisable();

        String getDefaultValue();

    }

    public enum DataType{
        INTEGER,DOUBLE,STRING,BOOLEAN,DATE,TIMESTAMP;
    }

}
