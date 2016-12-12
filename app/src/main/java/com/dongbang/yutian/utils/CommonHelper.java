package com.dongbang.yutian.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author duwei Create at 2013-3-13上午9:27:21
 * @ClassName CommonHelper
 * @description datacenter里面公用帮助类
 */
public class CommonHelper {

    private static final String TAG = CommonHelper.class.getSimpleName();
    private static CommonHelper commonHelper;
    public static ProgressDialog mProgress;

    // show the progress bar.
    public static ProgressDialog showProgress(Context context, CharSequence message) {
        return showProgress(context, message, false, null);
    }

    public static ProgressDialog showProgress(Context context, CharSequence message, DialogInterface.OnCancelListener listener) {
        return showProgress(context, message, false, listener);
    }

    public static ProgressDialog showProgressNotCancel(Context context, CharSequence message) {
        return showProgress(context, message, true, null);
    }

    public static ProgressDialog showProgress(Context context, CharSequence message, boolean cancelable, DialogInterface.OnCancelListener listener) {
        if (mProgress != null && context != null) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing()) {
                mProgress.dismiss();
            }
        }
        mProgress = new ProgressDialog(context);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setMessage(message);
        mProgress.setIndeterminate(false);
        mProgress.setCancelable(cancelable);
        mProgress.setOnCancelListener(listener);
        // mProgress.setCancelable(true);
        if (!mProgress.isShowing()) {
            mProgress.show();
        }
        return mProgress;
    }


    public static void closeProgress() {
        if (mProgress != null) {
            if (mProgress.isShowing()) {
                mProgress.dismiss();
            }
        }
    }


    public static boolean isProgerss() {
        if (mProgress != null)
            return true;

        return false;
    }

    // 等待框
    public static ProgressDialog progressDialog;

    /**
     * @param context
     * @param title   :正在定位...
     * @param msg     :请等待...
     */
    public static void openProgressDialog(Context context, String title, String msg) {
        progressDialog = ProgressDialog.show(context, title, msg, true, false);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);

    }

    /**
     * @param context
     * @param
     * @param
     */
    public static void closeProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 得到CommonHelper 实例
     */
    private CommonHelper() {
    }

    public static CommonHelper getInstance() {
        if (commonHelper == null) {
            commonHelper = new CommonHelper();
        }
        return commonHelper;
    }

    /**
     * @param @param  activity
     * @param @param  uri
     * @param @return
     * @return String
     * @throws
     * @Title: getPath
     * @Description: 得到Media.DATA数据
     */
    public String getPath(Activity activity, Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);

        activity.startManagingCursor(cursor);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    public View getFooterDividerView(Context context, int layout) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(layout, null);
    }

    /**
     * @param @param  context
     * @param @param  id
     * @param @return
     * @return View
     * @throws
     * @Title: getHeadDividerView
     * @Description: 获得xml布局的view
     */
    public View getHeadDividerView(Context context, int id) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(id, null);
    }

    /**
     * @param @param  context
     * @param @return
     * @return boolean
     * @throws
     * @Title: checkNetwork
     * @Description: 检测是否有网络
     */
    public boolean checkNetwork(Context context) {
        ConnectivityManager netManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = netManager.getActiveNetworkInfo();
        if (info != null)
            return info.isAvailable();
        else
            return false;
    }

    /**
     * @param @param  context
     * @param @return
     * @return String
     * @throws
     * @Title: checkNetworkType
     * @Description: 检测网络类型
     */
    public String checkNetworkType(Context context) {
        ConnectivityManager netManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = netManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable())
            return info.getTypeName();
        else
            return "";
    }

    /**
     * @param @param  context
     * @param @param  prefix
     * @param @return
     * @return String
     * @throws
     * @Title: getAppVersionName
     * @Description: 版本名字
     */
    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            } else {
                return versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * @param @param  context
     * @param @return
     * @return int
     * @throws
     * @Title: getVersionCode
     * @Description: 版本编号
     */
    public int getVersionCode(Context context) {
        int code = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
            return code;
        } catch (Exception e) {
            // LogUtils.E("VersionInfo", "Exception"+e);
        }
        return code;
    }

    /**
     * @param @param  context
     * @param @param  toastmsg
     * @param @return
     * @return boolean
     * @throws
     * @Title: CheckExternStorage
     * @Description: 检测外部存储设备
     */
    public boolean CheckExternStorage(Context context, String toastmsg) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        } else {
            if (context != null && toastmsg != null) {
                Toast.makeText(context, toastmsg, Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    public boolean CheckExternStorage(Context context, int toastmsgId) {
        if (context != null && toastmsgId > 0) {
            return CheckExternStorage(context, context.getResources().getString(toastmsgId));
        }
        return CheckExternStorage(null, null);
    }

    public void showListCountHint(TextView hintView, int count, int nodataHintId, int haveDataHintFormatId) {
        if (hintView != null) {
            if (count <= 0) {
                hintView.setVisibility(View.VISIBLE);
                if (nodataHintId > 0) {
                    hintView.setText(nodataHintId);
                }
            } else {
                if (haveDataHintFormatId > 0) {
                    hintView.setVisibility(View.VISIBLE);
                    String prompt = String.format(hintView.getResources().getString(haveDataHintFormatId), count);
                    hintView.setText(prompt);
                } else {
                    hintView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    /**
     * @param @param  mobiles
     * @param @return
     * @return boolean
     * @throws
     * @Title: isMobileNO
     * @Description: 检测是否符合手机号码规则
     */
    public boolean isMobileNO(String mobiles) {

        // Pattern p =
        // Pattern.compile("(^(13[0-9]|15[0-35-9]|14[57]|18[02356789])\\d{8}");
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[57]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    /**
     * @param @return
     * @return String
     * @throws
     * @Title: getMyUUID
     * @Description: 获得uuid
     */
    public String getMyUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;
    }

    /**
     * @param @param  context
     * @param @return
     * @return String
     * @throws
     * @Title: getMyIMEI
     * @Description:得到imei号
     */
    public String getMyIMEI(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = null;
        try {
            tmDevice = URLEncoder.encode(tm.getDeviceId(), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tmDevice;
    }


}
