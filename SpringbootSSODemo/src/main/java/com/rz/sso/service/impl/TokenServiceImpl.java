package com.rz.sso.service.impl;

import com.rz.sso.domain.TokenInfo;

import com.rz.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by as on 2017/12/24.
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void setToken(String tokenId, TokenInfo tokenInfo) {
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(tokenId, tokenInfo,60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delToken(String tokenId) {
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            if (redisTemplate.hasKey(tokenId)) {
                redisTemplate.delete(tokenId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TokenInfo getToken(String tokenId) {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        return (TokenInfo) operations.get(tokenId);
    }

    @Override
    public String get(String key) {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        return stringStringValueOperations.get(key);
    }

}
