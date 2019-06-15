package com.sucl.jpa.core.service.impl;

import com.sucl.jpa.core.db.DbType;
import com.sucl.jpa.core.service.DbTypeService;
import com.sucl.jpa.core.db.DbType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author sucl
 * @date 2019/6/13
 */
@Slf4j
@Component
public class DbTypeServiceImpl implements DbTypeService {
    @Autowired
    private DataSource dataSource;

    @Override
    public DbType getDbType() {
        String databaseId = getDatabaseId(dataSource);
        if(databaseId!=null){
            return DbType.valueOf(databaseId.toUpperCase());
        }
        return null;
    }

    private Properties properties;

    public String getDatabaseId(DataSource dataSource) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource cannot be null");
        }
        try {
            return getDatabaseName(dataSource);
        } catch (Exception e) {
            log.error("Could not get a databaseId from dataSource", e);
        }
        return null;
    }

    public void setProperties(Properties p) {
        this.properties = p;
    }

    private String getDatabaseName(DataSource dataSource) throws SQLException {
        String productName = getDatabaseProductName(dataSource);
        if (this.properties != null) {
            for (Map.Entry<Object, Object> property : properties.entrySet()) {
                if (productName.contains((String) property.getKey())) {
                    return (String) property.getValue();
                }
            }
            return null;
        }
        return productName;
    }

    private String getDatabaseProductName(DataSource dataSource) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            DatabaseMetaData metaData = con.getMetaData();
            return metaData.getDatabaseProductName();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // ignored
                }
            }
        }
    }
}

