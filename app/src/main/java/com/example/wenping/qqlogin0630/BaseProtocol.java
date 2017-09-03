package com.example.wenping.qqlogin0630;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;

<<<<<<< HEAD

=======
/**
 * ClassName:BaseProtocol
 * Author   :吴通
 * Email    :wutong@itcast.cn
 * Date     :2017/6/26
 * Description: 所有网络请求protocol的基类 get post 文件上传
 */
>>>>>>> a18008bca62debfe663c445f4f928b9c303879ed
//定义的泛型在loadData(final BaseProtocol.CallBack<RESPONSE> callBack)使用
public abstract class BaseProtocol<RESPONSE> {
    //所有具有刷新界面网络请求需要的页面数字段
    protected int pageIndex;
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    String url = getUrl();

    /**
     * 网络请求地址
     *
     * @return
     */
    protected abstract String getUrl();

    /**
     * 网络请求参数
     *
     * @return
     */
    protected Map<String, String> getParamMap() {
        return null;
    }

    /**
     * 添加网络请求头
     *
     * @return
     */
    protected Map<String, String> getHeaderMap() {
        return null;
    }

    /**
     * 传递上传文件的列表集合
     * @return
     */
    private Map<String, File> getFileMap() {
        return null;
    }

    /**
     * get请求
     *
     * @param callBack
     */
    public void loadDataByGet(final BaseProtocol.CallBack<RESPONSE> callBack, final int reqType) {
        OkHttpUtils
                .get()
                .url(url)
                .headers(getHeaderMap())
                .params(getParamMap())//通过url地址?key=value&key=value
//                .addParams("pageIndex", pageIndex + "")
//                .addParams("catalog", "1")
//                .addParams("pageSize", "20")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onError(call, e, id,reqType);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //获取传递的泛型类型
                        Type type = ((ParameterizedType) BaseProtocol.this.getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
                        //解析返回值
                        RESPONSE res = XmlUtils.toBean((Class<RESPONSE>) type, response.getBytes());
                        if (res == null) return;
                        callBack.onResponse(res, id,reqType);
                    }
                });
    }

    /**
     * post请求
     *
     * @param callBack
     */
    public void loadDataByPost(final BaseProtocol.CallBack<RESPONSE> callBack, final int reqType) {
        PostFormBuilder builder = OkHttpUtils
                .post()
                .url(this.url);
        //多文件上传需要通过multipart编码(boundary分割)
        Map<String, File> fileMap = getFileMap();
        if(fileMap!=null) {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                String key = entry.getKey();
                File file = entry.getValue();
                builder.addFile(key, file.getName(), file);
            }
        }
//        builder.addFile()

        builder.headers(getHeaderMap())
                .params(getParamMap())//通过流的方式key=value&key=value
//                .addParams("pageIndex", pageIndex + "")
//                .addParams("catalog", "1")
//                .addParams("pageSize", "20")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onError(call, e, id,reqType);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //获取传递的泛型类型
                        Type type = ((ParameterizedType) BaseProtocol.this.getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
                        //解析返回值
                        RESPONSE res = XmlUtils.toBean((Class<RESPONSE>) type, response.getBytes());
                        if (res == null) return;
                        callBack.onResponse(res, id,reqType);
                    }
                });
    }




    //定义的泛型 在onResponse使用
    public interface CallBack<RESPONSE> {
        void onError(Call call, Exception e, int id, int reqType);

        void onResponse(RESPONSE response, int id, int reqType);
    }
}
