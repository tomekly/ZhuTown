package com.dongbang.yutian.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.widget.LinearLayout;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.MessageEntity;
import com.dongbang.yutian.common.AppManager;
import com.dongbang.yutian.common.UIhelper;
import com.dongbang.yutian.retrofit.RetrofitFactory;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends BaseActivity {

    @Bind(R.id.username)
    AppCompatEditText edtUsername;
    @Bind(R.id.userPwd)
    AppCompatEditText edtUserPwd;
    @Bind(R.id.userLogin)
    AppCompatButton btnUserLogin;
    @Bind(R.id.activity_user_login)
    LinearLayout activityUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.userLogin)
    public void onClick() {

        String username = edtUsername.getText().toString().trim();
        String password = edtUserPwd.getText().toString().trim();
        verifyInfo(username, password);
    }

    /**
     * 验证账户信息
     *
     * @param username
     * @param password
     */
    private void verifyInfo(String username, String password) {
        if (username.length() > 20 || username.length() < 2) {
            ToastUtils.showShort(context, context.getResources().getString(R.string.error_msg_username_error));
            return;
        }
        if (password.length() < 6 || password.length() > 20) {
            ToastUtils.showShort(context, context.getResources().getString(R.string.error_msg_pssword_error));
            return;
        }

        requestLogin(username, password);
    }

    /**
     * 请求登录
     */
    private void requestLogin(String username, String password) {
        showDialog();
        Call<MessageEntity> call = RetrofitFactory.getRetrofitService(RetrofitFactory.BASE_URL).requestLogin(username, password);
        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                dissDialog();
                if (response.body().getCode() == 1) {
                    UIhelper.jumpActivityFinish(UserLoginActivity.this, MainActivity.class);
                } else {
                    ToastUtils.showShort(context, "登录失败");
                }
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                dissDialog();
                LogUtils.e(TAG, t.getMessage(), t);
            }
        });
    }




}
