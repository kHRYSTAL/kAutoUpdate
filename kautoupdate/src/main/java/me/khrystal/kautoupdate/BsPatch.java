package me.khrystal.kautoupdate;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class BsPatch {

    static {
        System.loadLibrary("bsdiff");
    }

    public static native int bspatch(String oldApkPath, String newApkPath, String patchPath);
}
