package com.example.wenping.okhttpdemos0711;

/**
 * Created by wenping on 7/12/2017.
 */

public class RegisterBean extends BaseBean{

    /**
     * userid : 15518
     */

    public UserInfoBean userInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        public String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "userid='" + userid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "userInfo=" + userInfo +
                '}';
    }
}