package com.rz.sso.service.impl;/**
 * Created by as on 2018/3/17.
 */

import com.rz.sso.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.print.PageFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author as
 * @create 2018-03-17 10:47
 * @desc UserService实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Environment env;//获取配置文件中的服务
    @Value("${database}")
    private String database;

    @Override
    public Map<String, Object> checkUserByConfigSql(String sqlColumnNames, Map<String, String[]> param) {
        Map<String, Object> result = null;
        try {
            if (StringUtils.isEmpty(database)) {
                return result;
            }

            StringBuffer whereStr = new StringBuffer();
            List<String> values = new ArrayList<>(2);//存放传递过来的参数值
            //拼接sql的where部分的语句
            for (Map.Entry<String, String[]> entry : param.entrySet()) {
                if ("service".equals(entry.getKey())) {
                    continue;
                }
                whereStr.append(" and ").append(entry.getKey()).append(" = ?");
                values.add(entry.getValue()[0]);
            }
            sqlColumnNames = env.getProperty(sqlColumnNames + ".sqlcolumn-name");
            if (StringUtils.isEmpty(sqlColumnNames)) {
                sqlColumnNames = "*";
            }
            //拼接完整sql
            StringBuffer sql = new StringBuffer();
            sql.append("select ").append(sqlColumnNames).append(" from ")
                    .append(database).append(" where 1=1 ").append(whereStr);

            //执行数据库查询
            result = (Map<String, Object>) jdbcTemplate.execute(sql.toString(), new PreparedStatementCallback() {
                public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                    for (int i = 0; i < values.size(); i++) {
                        ps.setString(i + 1, values.get(i));
                    }
                    ResultSet rs = ps.executeQuery();
                    Map<String, Object> result = null;
                    if (rs != null && rs.next()) {
                        result = new HashMap<String, Object>();
                        ResultSetMetaData metaData = rs.getMetaData();
                        int count = metaData.getColumnCount();//获取ResultSet中数据的列数
                        //遍历获的每一列的列名,采用反射机制设置值
                        for (int i = 1; i <= count; i++) {
                            String name = metaData.getColumnName(i);
                            Object obj = rs.getObject(i);
                            result.put(name, obj);
                        }
                    }
                    return result;
                }
            });
        } catch (Exception e) {
            logger.error("查询出错啦，错误详情：{}", e);
        }
        return result;
    }
}
