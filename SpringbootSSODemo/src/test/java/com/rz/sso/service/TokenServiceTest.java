package com.rz.sso.service;

import com.rz.sso.domain.TokenInfo;
import com.rz.sso.service.TokenService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Created by as on 2017/12/8.
 * 测试boyservice层
 * 不同分支branch的包名不同会导致这个错误spring boot Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration
 * <p/>
 * 在本分支test下添加@SpringBootTest(classes=Base.class)//base.java位于本分支下
 * 或者将包名改为与其他分支一样的路径。就可以了！
 * 简而言之：测试类的包名需要和被测试类的包名一样
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void setToken() {
        String token = UUID.randomUUID().toString();
        System.out.println(token);
        TokenInfo newTokenInfo = new TokenInfo();
        newTokenInfo.setSsoClient("xxx");
        newTokenInfo.setGlobalId("12345");
        tokenService.setToken(token, newTokenInfo);
    }

    @Test
    public void getToken() {
        TokenInfo token = tokenService.getToken("fbec6bf5-b674-4520-9a2a-ea1256d492af");
        System.out.println(token);
    }
    @Test
    public  void  get(){
        System.out.println(tokenService.get("k1").toString());
    }
}
