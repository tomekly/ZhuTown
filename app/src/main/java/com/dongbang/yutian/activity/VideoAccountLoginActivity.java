package com.dongbang.yutian.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.Toast;

import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Login_Info_t;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.fDPSDKStatusCallback;
import com.dongbang.yutian.R;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.utils.SharedPreferencesUtil;
import com.dongbang.yutian.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoAccountLoginActivity extends BaseActivity {

    @Bind(R.id.edt_ip)
    AppCompatEditText edtIp;
    @Bind(R.id.edt_port)
    AppCompatEditText edtPort;
    @Bind(R.id.edt_username)
    AppCompatEditText edtUsername;
    @Bind(R.id.edt_password)
    AppCompatEditText edtPassword;
    @Bind(R.id.btn_video_account_sign_in)
    AppCompatButton btnVideoAccountSignIn;
    static Return_Value_Info_t m_ReValue = new Return_Value_Info_t();

    static long m_loginHandle = 0;
    static int m_nLastError = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_account_login);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        int nType = 1;
        m_nLastError = IDpsdkCore.DPSDK_Create(nType, m_ReValue);
        IDpsdkCore.DPSDK_SetDPSDKStatusCallback(m_ReValue.nReturnValue, new fDPSDKStatusCallback() {
            @Override
            public void invoke(int nPDLLHandle, int nStatus) {
                Log.v("fDPSDKStatusCallback", "nStatus = " + nStatus);
            }
        });
    }


    Login_Info_t loginInfo = new Login_Info_t();

    private void executeLoginTask() {
        loginInfo.szIp = edtIp.getText().toString().getBytes();
        loginInfo.nPort = Integer.parseInt(edtPort.getText().toString());
        loginInfo.szUsername = edtUsername.getText().toString().getBytes();
        loginInfo.szPassword = edtPassword.getText().toString().getBytes();
        loginInfo.nProtocol = 2;
        VideoAccountSignIn signIn = new VideoAccountSignIn();
        signIn.execute();
    }

    @OnClick(R.id.btn_video_account_sign_in)
    public void onClick() {
        executeLoginTask();
    }

    public class VideoAccountSignIn extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            if (m_loginHandle != 0) {
                IDpsdkCore.DPSDK_Logout(m_ReValue.nReturnValue, 30000);
                m_loginHandle = 0;
            }
            return IDpsdkCore.DPSDK_Login(m_ReValue.nReturnValue, loginInfo, 30000);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {
                Log.d("DpsdkLogin success:", result + "");
                IDpsdkCore.DPSDK_SetCompressType(m_ReValue.nReturnValue, 0);
                m_loginHandle = 1;
                AppConfig.RETURN_VALUE =m_ReValue.nReturnValue;
                saveLoginInfo();
                Intent intent = new Intent(VideoAccountLoginActivity.this, MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("result",result);
                intent.putExtras(bundle);
                setResult(AppConfig.RESULT_CODE, intent);
                finish();
            } else {
                Log.d("DpsdkLogin failed:", result + "");
                Toast.makeText(getApplicationContext(), "login failed" + result, Toast.LENGTH_SHORT).show();
                m_loginHandle = 0;
            }
        }
    }


    /**
     * 保存登录信息
     */
    private void saveLoginInfo() {
        SharedPreferencesUtil.setString(this, "_IP", edtIp.getText().toString().trim());
        SharedPreferencesUtil.setInt(this, "port", Integer.parseInt(edtPort.getText().toString().trim()));
        SharedPreferencesUtil.setString(this, "username", edtUsername.getText().toString().trim());
        SharedPreferencesUtil.setString(this, "password", edtPassword.getText().toString().trim());
        SharedPreferencesUtil.setBoolean(this,"isExist",true);
        SharedPreferencesUtil.setLong(this,"loginHandle",m_loginHandle);
    }
}
