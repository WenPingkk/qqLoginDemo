package com.example.wenping.qqlogin0630;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 第一步,集成开发环境
     * 第二步,oncreate方法中初始化mtencent
     * 第三部,实现qq登录的接口回调
     * 第四步,登录按钮实现调用接口
     * 第五步,onComplete 方法中获取 token咯
     * 第六步,传入token,实现登录操作
     * 第七步,获取数据xxx
     */
    private Tencent mTencent;
    private String scope = "all";
    private Button mQqLogin;
    private AuthInfo mAuthInfo;
    private Button mSinaLogin;
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /*=============== 初始化TencentSDK ===============*/
// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
// 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("1106182861", this.getApplicationContext());
// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
// 初始化视图
        /*===============  ===============*/

    /*=============== 微博登录初始化 ===============*/
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
    }

    private void initView() {
        mQqLogin = (Button) findViewById(R.id.qqLogin);

        mQqLogin.setOnClickListener(this);
        mSinaLogin = (Button) findViewById(R.id.sinaLogin);
        mSinaLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qqLogin:
                loginQQ();
                break;
            case R.id.sinaLogin:
                loginSina();

                break;
        }
    }

    private void loginSina() {

        mSsoHandler. authorize(new AuthListener());
    }

    private void loginQQ() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", listener);
        }
    }
    private IUiListener listener = new BaseUiListener();

    /**
     * QQ登录的接口回调
     */

    private static final String TAG = "MainActivity";

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, o + "", Toast.LENGTH_LONG).show();
            Log.e(TAG, "onComplete " + o);


            JSONObject object = (JSONObject) o;
            try {
                String token = object.getString("access_token");
                LoginProtocol loginProtocol = new LoginProtocol();
                loginProtocol.setToken(token);
                loginProtocol.loadDataByPost(new BaseProtocol.CallBack<LoginUserBean>() {
                    @Override
                    public void onError(Call call, Exception e, int id, int reqType) {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(LoginUserBean bean, int id, int reqType) {
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_LONG).show();
                        Result result = bean.getResult();
                        if (result.getErrorCode() == 0) {
                            // 06:5C:D3:57:F2:88:94:FD:FE:5A:87:67:6A:EF:62:B3
                            String str = " 06:5C:D3:57:F2:88:94:FD:FE:5A:87:67:6A:EF:62:B3";
                            String s = str.replace(":", "");
                            String lowerCase = s.toLowerCase();
                            String trim = lowerCase.trim();
                            Log.e(TAG, trim);

                            Log.e(TAG, "未绑定用户信息");
                        } else {
                            Log.e(TAG, "获取用户信息");
                            //String s = bean.getUser().toString();
                            //bean.getUser().getxxx
                        }
                    }
                }, 1);


                Log.e(TAG, "onComplete " + token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError error) {
            Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "授权取消", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);

        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    /*=============== 微博接口回调 ===============*/
    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle bundle) {
            Toast.makeText(MainActivity.this, "微博授权成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(MainActivity.this, "微博授权失败", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "微博授权取消", Toast.LENGTH_LONG).show();
        }
    }
}


