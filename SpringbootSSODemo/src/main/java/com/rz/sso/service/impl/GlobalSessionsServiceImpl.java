package com.rz.sso.service.impl;/**
 * Created by as on 2018/3/25.
 */

import com.rz.sso.domain.TokenInfo;
import com.rz.sso.service.GlobalSessionsService;
import com.rz.sso.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author as
 * @create 2018-03-25 19:29
 * @desc 操作全局会话的服务类
 */
@Service("globalSessionsService")
public class GlobalSessionsServiceImpl implements GlobalSessionsService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void saveGlobalSession(String sessionId, TokenInfo tokenInfo) {
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(KeyUtils.getSessionKey(sessionId), tokenInfo, 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TokenInfo getGlobalSession(String sessionId) {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        return (TokenInfo) operations.get(KeyUtils.getSessionKey(sessionId));
    }

    @Override
    public void updatetGlobalSessionIdExpireTime(String sessionId) {
        redisTemplate.expire(KeyUtils.getSessionKey(sessionId), 30, TimeUnit.MINUTES);//设置过期时间
    }

    @Override
    public void deleteGlobalSession(String sessionId) {
        redisTemplate.delete(KeyUtils.getSessionKey(sessionId));
    }
}
