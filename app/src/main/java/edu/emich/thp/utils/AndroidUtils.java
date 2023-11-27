package edu.emich.thp.utils;

import android.content.Context;
import android.content.res.Configuration;

public class AndroidUtils {
    /**
     * Whether or not the application is running in night mode/dark theme
     * @param context
     * @return
     */
    public static boolean isNightMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}
