package com.smnadim21.api.tools;

import android.app.Application;

public class GetContext {
    public static Application getContext() {
        try {
            return (Application) Class.forName("android.app.AppGlobals")
                    .getMethod("getInitialApplication").invoke(null, (Object[]) null);
        } catch (Exception e) {
            return null;
        }

    }
}
