package com.flutter_webview_plugin;

import android.view.View;
import android.webkit.WebView;

class FullScreenWebView extends WebView {
    boolean mNavVisible;
    boolean mFullScreen = false;
    int mLastSystemUiVis;

    Runnable mNavHider = new Runnable() {
        @Override public void run() {
            setNavVisibility(false);
        }
    };

    public setFullScreen(boolean fullScreen) {
        mFullScreen = fullScreen;
        if (fullScreen) {
            setOnSystemUiVisibilityChangeListener(this);
            setOnClickListener(this);
        }
    }

    public FullScreenWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public void onSystemUiVisibilityChange(int visibility) {
        // Detect when we go out of nav-hidden mode, to clear our state
        // back to having the full UI chrome up.  Only do this when
        // the state is changing and nav is no longer hidden.
        int diff = mLastSystemUiVis ^ visibility;
        mLastSystemUiVis = visibility;
        if ((diff&SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0
                && (visibility&SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
            setNavVisibility(true);
        }
    }

    @Override public void onClick(View v) {
        setNavVisibility(true);
    }

    void setNavVisibility(boolean visible) {
        int newVis = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (!visible) {
            newVis |= SYSTEM_UI_FLAG_LOW_PROFILE | SYSTEM_UI_FLAG_FULLSCREEN
                    | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // If we are now visible, schedule a timer for us to go invisible.
        if (visible) {
            Handler h = getHandler();
            if (h != null) {
                h.removeCallbacks(mNavHider);
                h.postDelayed(mNavHider, 3000);
            }
        }

        // Set the new desired visibility.
        setSystemUiVisibility(newVis);
    }
}