package com.dongbang.yutian.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.UploadEntity;
import com.dongbang.yutian.beans.VersionBean;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.common.AppContext;
import com.dongbang.yutian.common.AppManager;
import com.dongbang.yutian.retrofit.RetrofitFactory;
import com.dongbang.yutian.service.DownloadService;
import com.dongbang.yutian.utils.BaseUtils;
import com.dongbang.yutian.utils.CommonHelper;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;
import com.dongbang.yutian.utils.statusbar.StatusBarUtil;
import com.dongbang.yutian.view.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DongBang on 2016/5/4.
 */
public class BaseActivity extends AppCompatActivity {

    public String TAG = "BaseActivity";// 打印日志的标题
    private boolean isExit = true;
    private static final int HANDLER_IS_EXIT = 1000;
    public Context context;
    private Toolbar toolbar;
    private TextView appbar_title;

    public LoadingDialog dialog = null;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_IS_EXIT:
                    isExit = true;
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        context = AppContext.getInstance().getContext();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    public void initToolbar(Toolbar toolbar, CharSequence title, int bgColor) {
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**
         * 实现双击返回键，退出应用程序
         * */
        if (keyCode == KeyEvent.KEYCODE_BACK && this.getClass().getSimpleName().equals("MainActivity")) {//判断是否点击返回键
            if (isExit) {
                isExit = false;//更改判断
                ToastUtils.showShort(getApplicationContext(), "在按一次退出应用");//提示语
                Message msg = new Message();
                msg.what = HANDLER_IS_EXIT;
                handler.sendMessageDelayed(msg, 2000);//延时2秒发送消息
            } else {
                AppManager.getAppManager().finishAllActivity();//销毁所有的Activity
                System.exit(0);//退出程序
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void initToolBar(int toolbarId, int titleid, int iconId) {
        toolbar = (Toolbar) findViewById(toolbarId);
        toolbar.setTitle("");
        appbar_title = (TextView) findViewById(titleid);
        if (iconId != 0) {
            toolbar.setNavigationIcon(iconId);
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //设置标题的公共方法
    public void setToolbarTitle(String title) {
        appbar_title.setText(title);
    }

    public void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }


    public void showDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
        }
        dialog.show();
    }

    public void dissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    private VersionBean versionBean;

    public void checkNewVersion() {
        showDialog();
        Call<VersionBean> call = RetrofitFactory.getRetrofitService(RetrofitFactory.BASE_URL).getNewVersion();
        call.enqueue(new Callback<VersionBean>() {
            @Override
            public void onResponse(Call<VersionBean> call, final Response<VersionBean> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dissDialog();
                        versionBean = response.body();
                        isDowloadApk(versionBean);
                    }
                });

            }

            @Override
            public void onFailure(Call<VersionBean> call, Throwable t) {
                dissDialog();
                LogUtils.e(TAG, "onFailure 165", t);
            }
        });
    }

    private void startDownload() {
        AppConfig.DOWNLOAD_URL = versionBean.getDownload();
        Intent intent = new Intent(BaseActivity.this, DownloadService.class);
        startService(intent);
    }

    private void isDowloadApk(final VersionBean versionBean) {
        int curVersionCode = BaseUtils.getVersionCode(context);
        if (curVersionCode < versionBean.getVno()) {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setMessage("版本更新,当前版本：" + BaseUtils.getVersionName(context) + "检测到新版本" + versionBean.getName() + "是否现在升级?");
            dialog.setTitle("版本更新");
            SpannableStringBuilder builder = new SpannableStringBuilder("确定");
            builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_458F14)), 0, 2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            SpannableStringBuilder builder1 = new SpannableStringBuilder("取消");
            builder1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_afafaf)), 0, 2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, builder, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    } else {
                        startDownload();
                    }
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, builder1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownload();
            } else {
                ToastUtils.showShort(context, "请授权下载");
            }
        }
    }

    public void uploadFinished() {
    }
}
