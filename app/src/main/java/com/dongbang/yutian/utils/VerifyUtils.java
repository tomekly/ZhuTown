package com.dongbang.yutian.utils;

import java.util.regex.Pattern;

/**
 * Created by DongBang on 2016/10/11.
 */

public class VerifyUtils {

    /**
     * 验证密码
     * @param password
     * @return 以字母开头，长度在6-18之间，只能包含字符、数字和下划线
     */
    public static boolean checkPasswrd(String password)
    {
        Pattern pattern= Pattern.compile("^[a-zA-Z]\\w{5,19}$");
        return pattern.matcher(password).find();
    }
}
