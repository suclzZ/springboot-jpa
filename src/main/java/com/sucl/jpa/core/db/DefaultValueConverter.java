package com.sucl.jpa.core.db;

/**
 * 默认值处理
 * 比如字符串 默认值为 'xx'
 * 数字  123
 * 时间 to_data('2019-01-01','yyyy-mm-dd')
 *  ....
 * @author sucl
 * @date 2019/6/13
 */
public interface DefaultValueConverter {

    String defaultValue(TableCreateHelper.DataType dataType,String defaultValue);
}
