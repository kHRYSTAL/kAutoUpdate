package me.khrystal.kautoupdate.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class CheckType {

    public static final int DEFAULT_TYPE_COUNT = 2;

    @UpdateCheckType
    public static final int CHECK_BY_JSON = 0;

    @UpdateCheckType
    public static final int CHECK_BY_XML = 1;

    @IntDef({CHECK_BY_JSON, CHECK_BY_XML})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UpdateCheckType {

    }
}
