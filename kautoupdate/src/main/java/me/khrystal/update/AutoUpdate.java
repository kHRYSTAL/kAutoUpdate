package me.khrystal.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import me.khrystal.update.utils.CheckType;
import me.khrystal.update.utils.CheckUtil;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class AutoUpdate {

    private static Context mApplicationContext;
    private static String appVersionName;
    private static String appVersionCode;
    private static int osVersion;
    private static String DeviceUniqueID;

    public static void init(Context context) {
        mApplicationContext = context;
        // unique
        if (DeviceUniqueID == null) {
            DeviceUniqueID =
                    android.provider.Settings.Secure.getString(context.getContentResolver(),
                            android.provider.Settings.Secure.ANDROID_ID);
            if (DeviceUniqueID == null) {
                DeviceUniqueID = String.valueOf(System.currentTimeMillis() % 6000);
            }
        }
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersionName = info.versionName;
            appVersionCode = String.valueOf(info.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        osVersion = Build.VERSION.SDK_INT;
    }

    public static Context getContext() {
        return mApplicationContext;
    }

    public static void checkRemote(@CheckType.UpdateCheckType int type) {
        CheckUtil.check(type);
    }

}
