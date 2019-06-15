package com.sucl.jpa.core.method.convert;

import com.sucl.jpa.core.util.DateUtils;
import com.sucl.jpa.core.util.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 无效了...
 * @author sucl
 * @date 2019/4/3
 */
@Component
public class StringDateConvert implements Converter<String,Date> {

    @Override
    public Date convert(String source) {
        if(DateUtils.isDate(source)){
            return DateUtils.getDate(source);
        }
        return null;
    }
}
