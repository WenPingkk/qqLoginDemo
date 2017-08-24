package com.example.wenping.qqlogin0630;

import java.util.HashMap;
import java.util.Map;


public class LoginProtocol extends BaseProtocol<LoginUserBean>{
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected String getUrl() {
        return "http://www.oschina.net/action/api/openid_login";
    }

    @Override
    protected Map<String, String> getParamMap() {
        Map<String,String> map = new HashMap<>();
        map.put("cateloge","qq");
        map.put("openid_info",token);
        return map;
    }
}
