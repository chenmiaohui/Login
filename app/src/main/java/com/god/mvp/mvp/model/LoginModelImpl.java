package com.god.mvp.mvp.model;

import android.util.Log;

import com.god.mvp.mvp.bean.User;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author David  create on 2016/7/29  15:47.
 * @email david.forever.god@gmail.com
 * Learn from yesterday, live for today, hope for tomorrow.
 */
public class LoginModelImpl implements LoginModel {

    private OkHttpClient client = new OkHttpClient();
    @Override
    public void login(final String username, final String password, final OnLoginListener onLoginListener) {

        if (username.isEmpty()){
            onLoginListener.loginFailed("用户名不能为空");
            return;
        }
        if (password.isEmpty()){
            onLoginListener.loginFailed("密码不能为空");
            return;
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("accountName",username);//"zzsDev"
        builder.add("password",password);//"Dev12345"
        Request request = new Request.Builder()
                .url("www.baidu.com")//换成自己的URI
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                onLoginListener.loginFailed(e+"--"+request);
                Log.e("--------------","=============="+request+"========="+e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    Log.e("--------------","=============="+jo);
                    String code = jo.getString("code");
                    if (code.equals("1111")){
                        onLoginListener.loginSuccess(new User(username, password));
                    }else {
                        onLoginListener.loginFailed("账户名或者密码错误...");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Gson gson = new Gson();
//                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
//                if (loginBean.getCode().equals("1111")){
//                    onLoginListener.loginSuccess(new User(username, password));
//                }else{
//                    onLoginListener.loginFailed("登陆失败...");
//                }
//                Log.e("--------------","=============="+response.body().string()+"----"+new User(username, password));
            }
        });

    }
}
