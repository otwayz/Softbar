package com.otway.library;

import android.support.annotation.ColorRes;
import android.view.View;

/**
 * Created by Otway on 2018/1/15.
 */

public interface IBar {
	IBar statusBarDarkFont();

	IBar statusBarLightFont();

	IBar statusBarBackground(int statusBarColor);

	IBar statusBarBackgroundRes(@ColorRes int statusBarColorRes);

	IBar needOffsetView(View needOffsetView);

	IBar invasionStatusBar();

	IBar safeDarkFont(int statusBarColor);

	IBar safeDarkFont();

	IBar safeDarkFontRes(@ColorRes int statusBarColorRes);

	IBar safeLightFont(int statusBarColor);

	IBar safeLightFont();

	IBar safeLightFontRes(@ColorRes int statusBarColorRes);
}
