package com.rz.sso.service;/**
 * Created by as on 2018/3/17.
 */

import java.util.Map;

/**
 * @author as
 * @create 2018-03-17 10:43
 * @desc 用户相关操作的服务层
 */
public interface UserService {

    /**
     * 根据传递的参数拼接语句，查询数据库
     * @param param
     * @return
     */
    Map<String, Object> checkUserByConfigSql(String sqlColumnNames,Map<String, String[]> param);
}
