package com.example.wenping.okhttpdemos0711;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wenping on 7/11/2017.
 */

public class LoginActivity extends Activity implements View.OnClickListener {


    private Button mQqLogin;
    private Button mSinaLogin;
    private String url = "http://www.oschina.net/action/api/news_list?pageIndex=0&catalog=1&pageSize=20";
    private Button mOkHttpGet;
    private Button mOkHttpPost;
    private OkHttpClient mOkHttpClient;
    private Button mOkHttpRegister;
    private Button mOkHttpLogin;
    private EditText mNameAndPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mOkHttpClient = new OkHttpClient();
    }

    private void initView() {
        mOkHttpGet = (Button) findViewById(R.id.OkHttp_get);
        mOkHttpPost = (Button) findViewById(R.id.OkHttp_post);

        mOkHttpGet.setOnClickListener(this);
        mOkHttpPost.setOnClickListener(this);
        mOkHttpRegister = (Button) findViewById(R.id.OkHttp_register);
        mOkHttpRegister.setOnClickListener(this);
        mOkHttpLogin = (Button) findViewById(R.id.OkHttp_login);
        mOkHttpLogin.setOnClickListener(this);
        mNameAndPassword = (EditText) findViewById(R.id.nameAndPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OkHttp_get:
//                okhttpGet();
                okHttpGetFile();
                break;
            case R.id.OkHttp_post:
                okhttpPost();
                break;
            case R.id.OkHttp_register:
                mOkHttpRegister();
                break;
        }
    }

    private void mOkHttpRegister() {
        post();
    }

    public void post() {
        final String registerUrl = "http://10.0.3.2:8080/market/register";//?username=test22&password=test22
        //MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody = new FormBody.Builder()
                        .add("username", mNameAndPassword.getText().toString())
                        .add("password", mNameAndPassword.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url(registerUrl)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure ");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String myResponse = response.body().string();
                        Log.e(TAG, "myResponse"+myResponse);
                        /**使用gson可以实现*/
                        //Gson mGson = new Gson();
                        //RegisterBean bean = mGson.fromJson(myResponse, RegisterBean.class);
                        /**使用fastjson可以实现*/
                        RegisterBean bean = JSON.parseObject(myResponse, RegisterBean.class);

                        if (bean != null) {
                            Log.e(TAG, "bean!=null");
                            if (bean.getError_code() == null) {
                                Log.e(TAG, "userid:" + bean.getUserInfo().getUserid());
                            }else{
                                Log.e(TAG, bean.getError().toString());
                            }
                        } else {
                            Log.e(TAG, bean.getError().toString());
                        }
                    }
                });
            }
        }).start();
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String string = (String) msg.obj;
                Log.e(TAG, "handleMessage:" + string);
            }
        }
    };

    private void okHttpGetFile() {
        mThread.start();
    }

    /**
     * 程序可以运行,但是因为thread没有停止,所以按钮按第二次就完犊子了
     */
    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {

            Request request = new Request.Builder().get().url(url).build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "onFailure", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Message msg = Message.obtain();
                    msg.obj = response.toString();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "onResponse", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    });

    private void okhttpPost() {
    }

    private static final String TAG = "LoginActivity";

    /*=============== okhttp实现get请求数据 ===============*/
    /*--------------- 顺便复习了handler的用法 ---------------*/
    private void okhttpGet() {
        Toast.makeText(this, "点击了", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();
                try {
                    final Response response = mOkHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.e(TAG, "success");
                        Log.e(TAG, response.body().string() + "");

                        Message msg = Message.obtain();
                        String string = response.toString();
                        msg.obj = string;
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplication(), "请求成功了", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Log.e(TAG, "failed");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplication(), "请求失败", Toast.LENGTH_LONG).show();
                                Log.e(TAG, "failed");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run " + e);
                }
            }
        }).start();
    }
}
