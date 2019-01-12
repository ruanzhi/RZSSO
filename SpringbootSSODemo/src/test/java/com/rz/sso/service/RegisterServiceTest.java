package com.rz.sso.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Created by as on 2017/12/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterServiceTest {
    @Autowired
    private RegisterService registerService;

    @Test
    public void registerAppLogoutURL() {
        registerService.registerAppLogoutURL("1", "1", "1");
        registerService.registerAppLogoutURL("1", "2", "2");
    }

    @Test
    public void getAppLogoutURL() {
        Map<String, String> map = registerService.getAppLogoutURL("1");
        System.out.println(map);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String localId = entry.getKey();
                String appLogoutURL = entry.getValue();
                System.out.println(appLogoutURL + "?localId=" + localId);
            }
        }

    }
    @Test
    public void delAppLogoutURL() {
        registerService.delAppLogoutURL("1");
    }
}
