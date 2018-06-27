package com.cn.hhly.common.interceptor;

import com.alibaba.druid.sql.SQLUtils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StatementInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Properties;

/** 打印 MariaDB 的执行语句(基于 mysql 驱动的拦截器) */
public class ShowSqlInterceptor implements StatementInterceptor {
    Logger logger = LoggerFactory.getLogger(ShowSqlInterceptor.class);
    public void init(Connection connection, Properties properties) throws SQLException {}

    public ResultSetInternalMethods preProcess(String sql, Statement statement,
                                               Connection connection) throws SQLException {
        if (sql !=null && statement != null) {
            sql = statement.toString();
            if (sql!=null && sql.indexOf(':') > 0) {
                sql = SQLUtils.formatMySql(sql.substring(sql.indexOf(':') + 1).trim());
            }
        }
        if (sql != null) {
            if (logger.isDebugEnabled()) {
                logger.debug(sql);
                //LogUtil.SQL_LOG.debug("/* begin */\n{}\n/* end.. */", sql);
            }
        }
        return null;
    }

    public ResultSetInternalMethods postProcess(String s, Statement statement,
                 ResultSetInternalMethods resultSetInternalMethods, Connection connection) throws SQLException {
        return null;
    }

    public boolean executeTopLevelOnly() { return false; }
    public void destroy() {}
}
