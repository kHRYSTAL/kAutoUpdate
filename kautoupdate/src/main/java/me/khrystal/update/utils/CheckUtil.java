package me.khrystal.update.utils;

import me.khrystal.update.bean.UpdateResult;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class CheckUtil {
    public static UpdateResult check(final @CheckType.UpdateCheckType int checkType) {
        UpdateResult result = null;
        switch (checkType) {
            case CheckType.CHECK_BY_JSON:

                break;
            case CheckType.CHECK_BY_XML:

                break;
        }
        return result;
    }
}
