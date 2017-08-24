package com.example.wenping.qqlogin0630;

/**
 * Created by wenping on 7/11/2017.
 */

public interface Constants {
    public static final String APP_KEY = "2447593465"; // 应用的APP_KEY
    public static final String REDIRECT_URL = "http://www.baidu.com";// 应用的回调页
    public static final String SCOPE = // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
