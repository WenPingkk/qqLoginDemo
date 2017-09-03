package com.example.wenping.qqlogin0630;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD

=======
/**
 * ClassName:LoginProtocol
 * Author   :吴通
 * Email    :wutong@itcast.cn
 * Date     :2017/6/29
 * Description:
 */
>>>>>>> a18008bca62debfe663c445f4f928b9c303879ed
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
