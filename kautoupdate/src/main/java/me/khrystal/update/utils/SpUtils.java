package me.khrystal.update.utils;

import android.content.Context;
import android.content.SharedPreferences;

import me.khrystal.update.AutoUpdate;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class SpUtils {

    public static final String PREF_NAME = "update_preferences";
    public static final String VERSION_KEY = "version_key";
    public static final String IGNORE_UPDATE = "ignore_update";

    public static SharedPreferences getPrefs() {
        int code = Context.MODE_MULTI_PROCESS;
        return AutoUpdate.getContext().getSharedPreferences(PREF_NAME, code);
    }

    public static SharedPreferences.Editor getPrefsEditor() {
        return getPrefs().edit();
    }

    /**
     * save new version
     * @param newVersion
     */
    public static void setNewVersion(String newVersion) {
        SharedPreferences.Editor edit = getPrefsEditor();
        SharedPreferences prefs = getPrefs();
        String version = prefs.getString(VERSION_KEY, "");
        if (!version.equals(newVersion)) {
            edit.putBoolean(IGNORE_UPDATE, false);
        }
        edit.putString(VERSION_KEY, newVersion);
        edit.commit();
    }

    /**
     * get new version
     * @return
     */
    public static String getLocalNewVersion() {
        SharedPreferences prefs = getPrefs();
        String version = prefs.getString(VERSION_KEY, "");
        return version;
    }

    /**
     * @return
     */
    public static boolean getIgnoreUpdate() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(IGNORE_UPDATE, false);
    }

    /**
     * @param ignoreUpdate
     */
    public static void setIgnoreUpdate(boolean ignoreUpdate) {
        SharedPreferences.Editor edit = getPrefsEditor();
        edit.putBoolean(IGNORE_UPDATE, ignoreUpdate);
        edit.commit();
    }


}
