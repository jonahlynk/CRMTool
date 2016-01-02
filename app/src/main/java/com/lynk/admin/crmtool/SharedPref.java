package com.lynk.admin.crmtool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * This class handles the all the shared preference operation.
 * .i.e., creating shared preference and to set and get value.
 *
 * @author Jeevanandhan
 */

public class SharedPref implements AppConstants.SharedConstants {


    // Single ton objects...
    private static SharedPreferences preference = null;
    private static SharedPref sharedPref = null;

    //Single ton method for this class...
    public static SharedPref getInstance() {
        if (sharedPref != null) {
            return sharedPref;
        } else {
            sharedPref = new SharedPref();
            return sharedPref;
        }
    }


    /**
     * Singleton object for the shared preference.
     *
     * @param context
     * @return SharedPreferences
     */

    private SharedPreferences getPreferenceInstance(Context context) {

        if (preference != null) {
            return preference;
        } else {
            preference = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            return preference;
        }
    }

    /**
     * Set the String value in the shared preference with the given key.
     *
     * @param context
     * @param key
     * @param value
     */

    public void setSharedValue(Context context, String key, String value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * Set the Integer value in the shared preference with the given key.
     *
     * @param context
     * @param key
     * @param value
     */

    public void setSharedValue(Context context, String key, int value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Set the boolean value in the shared preference with the given key.
     *
     * @param context
     * @param key
     * @param value
     */

    public void setSharedValue(Context context, String key, Boolean value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Get Boolean value for the given key.
     *
     * @param context
     * @param key
     * @return Boolean
     */

    public Boolean getBooleanValue(Context context, String key) {
        return getPreferenceInstance(context).getBoolean(key, false);
    }

    /**
     * Get Integer value for the given key.
     *
     * @param context
     * @param key
     * @return Int
     */

    public int getIntValue(Context context, String key) {
        return getPreferenceInstance(context).getInt(key, 0);
    }


    /**
     * Get String value for the given key.
     *
     * @param context
     * @param key
     * @return String
     */

    public String getStringValue(Context context, String key) {
        return getPreferenceInstance(context).getString(key, null);
    }

}
