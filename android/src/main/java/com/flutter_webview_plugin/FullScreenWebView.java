package com.flutter_webview_plugin;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;

class FullScreenWebView extends WebView implements View.OnSystemUiVisibilityChangeListener {
    boolean mNavVisible;
    boolean mFullScreen = true;
    int mLastSystemUiVis;

    Runnable mNavHider = new Runnable() {
        @Override public void run() {
            setNavVisibility(false);
        }
    };

    public void setFullScreen(boolean fullScreen) {
        mFullScreen = fullScreen;
        setNavVisibility(false);
    }

    public FullScreenWebView(Activity activity) {
        super(activity);
        setOnSystemUiVisibilityChangeListener(this);
        setOnClickListener(this);
    }

    @Override public void onSystemUiVisibilityChange(int visibility) {
        // Detect when we go out of nav-hidden mode, to clear our state
        // back to having the full UI chrome up.  Only do this when
        // the state is changing and nav is no longer hidden.
        int diff = mLastSystemUiVis ^ visibility;
        mLastSystemUiVis = visibility;
        if ((diff & SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0 && (visibility & SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
            setNavVisibility(true);
        }
    }

    // @Override public void onClick(View v) {
    //     setNavVisibility(true);
    // }

    void setNavVisibility(boolean visible) {
        if (!mFullScreen) {
            return;
        }
        int newVis = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (!visible) {
            newVis |= SYSTEM_UI_FLAG_LOW_PROFILE | SYSTEM_UI_FLAG_FULLSCREEN
                    | SYSTEM_UI_FLAG_HIDE_NAVIGATION | SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        // If we are now visible, schedule a timer for us to go invisible.
        // if (visible) {
        //     Handler h = getHandler();
        //     if (h != null) {
        //         h.removeCallbacks(mNavHider);
        //         h.postDelayed(mNavHider, 10000);
        //     }
        // }

        // Set the new desired visibility.
        setSystemUiVisibility(newVis);
    }
}