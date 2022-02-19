package com.yunqi.starter.database.configuration;

import org.nutz.dao.ConnCallback;
import org.nutz.dao.impl.sql.run.NutDaoRunner;
import org.nutz.lang.Lang;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 集成Nutz事务和拦截链
 * Created by @author JsckChin on 2022/1/29
 */
public class SpringDaoRunner extends NutDaoRunner {

    @Override
    public void _run(DataSource dataSource, ConnCallback callback) {

        Connection con = DataSourceUtils.getConnection(dataSource);
        try {
            callback.invoke(con);
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
        finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }
}
