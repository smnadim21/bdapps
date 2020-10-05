package com.smnadim21.api.tools;

import android.widget.Toast;

public class Toaster {
    public static void ShowLogToast(String message) {
        Toast.makeText(GetContext.getContext(), "" + message, Toast.LENGTH_LONG).show();
    }

    static void ShowShortToast(String message) {
        Toast.makeText(GetContext.getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }
}
