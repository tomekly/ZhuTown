package com.dongbang.yutian.utils.statusbar;

import android.os.Environment;

/**
 * Created by adu on 16/5/3.
 */
public class YqzjConfig {
    /**
     * 是否输出日志
     */
    public static final boolean IS_DEBUG = true;

    /**
     * -------------------------------------------------Bundle key-------------------------------------------------------------------------------
     */
    public interface BundleKey {
        String TYPE = "TYPE";
        String VALUE = "VALUE";
        String TAB_INDEX = "TAB_INDEX";
        String TAG = "TAG";
        String TITLE = "TITLE";
        String ID = "ID";
        String PAGE = "PAGE";
        String LOGIN= "LOGIN";
    }

    public interface ConstantCode {
        String CHANGE_PWD_FOR_PHONE = "change_pwd_for_phone";//修改密码 通过手机获得验证码
        String CACHE_ROOT_DIR_NAME = "ooch";
        String UIL_BASE_PATH = "ooch/ooch/img";
        String HTTP_PREFIX = "http://";
        String DISK_BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CACHE_ROOT_DIR_NAME + "/" + CACHE_ROOT_DIR_NAME;
        String DISK_IMAGE_CACHE_PATH_2 = DISK_BASE_PATH + "/img2";
        String DISK_IMAGE_CACHE_PATH_3 = DISK_BASE_PATH + "/img3/img";
        String DISK_TAKE_PHOTO_PATH = DISK_BASE_PATH + "/temp";
        String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CACHE_ROOT_DIR_NAME + "/" + "download";
    }


}
