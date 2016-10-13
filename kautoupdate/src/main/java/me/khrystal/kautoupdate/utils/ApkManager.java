package me.khrystal.kautoupdate.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class ApkManager {

    public static String getApkPath(Context context) {
        context = context.getApplicationContext();
        ApplicationInfo info = context.getApplicationInfo();
        String apkPath = info.sourceDir;
        return apkPath;
    }
}
