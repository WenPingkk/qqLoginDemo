package com.example.wenping.okhttpdemos0711;

/**
 * Created by dada on 2017/6/14.
 */


public class BaseBean {


    /**
     * error : 用户名不存在或密码错误
     * error_code : 1530
     * response : error
     */

    public String error;
    public String error_code;

    @Override
    public String toString() {
        return "BaseBean{" +
                "error='" + error + '\'' +
                ", error_code='" + error_code + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public String response;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
