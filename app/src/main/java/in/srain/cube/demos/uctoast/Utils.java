package in.srain.cube.demos.uctoast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public final class Utils {

    private final static String LOG_TAG = "uc-toast";

    public static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

    public static void printIntent(String tag, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            Log.d(LOG_TAG, String.format("%s, intent: %s", tag, intent));
            return;
        }

        Bundle bundle = intent.getExtras();
        Log.d(LOG_TAG, String.format("%s, intent: %s, %s", tag, intent, bundleToString(bundle)));
    }
}
