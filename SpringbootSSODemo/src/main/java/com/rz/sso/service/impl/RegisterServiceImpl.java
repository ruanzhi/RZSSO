package com.rz.sso.service.impl;

import com.rz.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by as on 2017/12/24.
 */
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void registerAppLogoutURL(String key, String hashKey, String value) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    @Override
    public Map<String, String> getAppLogoutURL(String key) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        return hash.entries(key);
    }

    @Override
    public void delAppLogoutURL(String key) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        hash.delete(key, hash.entries(key).keySet().toArray());
    }
}
