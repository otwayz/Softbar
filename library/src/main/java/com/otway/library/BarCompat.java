package com.otway.library;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Otway on 2018/1/15.
 */
class BarCompat implements IBar {
	private static final int INVALID_COLOR = Integer.MIN_VALUE;

	private WeakReference<Activity> mActivityRef;
	private static boolean mIsSupported;

	static {
		mIsSupported = BarUtils.isSupportedStatusModeChange();
	}

	BarCompat(Activity activity) {
		mActivityRef = new WeakReference<>(activity);
	}

	private boolean isValid() {
		return mIsSupported && mActivityRef != null && mActivityRef.get() != null;
	}

	@Override
	public IBar statusBarDarkFont() {
		if (isValid()) {
			BarUtils.setStatusBarDarkFont(mActivityRef.get(), true);
		}
		return this;
	}

	@Override
	public IBar statusBarLightFont() {
		if (isValid()) {
			BarUtils.setStatusBarDarkFont(mActivityRef.get(), false);
		}
		return this;
	}

	@Override
	public IBar statusBarBackground(int statusBarColor) {
		if (isValid()) {
			BarUtils.setStatusBarColor(mActivityRef.get(), statusBarColor);
		}
		return this;
	}

	@Override
	public IBar statusBarBackgroundRes(int statusBarColorRes) {
		if (isValid()) {
			BarUtils.setStatusBarColor(mActivityRef.get(), ContextCompat.getColor(mActivityRef.get(), statusBarColorRes));
		}
		return this;
	}

	@Override
	public IBar needOffsetView(View needOffsetView) {
		if (isValid()) {
			BarUtils.needOffsetView(mActivityRef.get(), needOffsetView);
		}
		return this;
	}

	@Override
	public IBar invasionStatusBar() {
		if (isValid()) {
			BarUtils.invasionStatusBar(mActivityRef.get());
		}
		return this;
	}

	@Override
	public IBar safeDarkFont(int statusBarColor) {
		if (isValid()) {
			boolean succeed = BarUtils.setStatusBarDarkFont(mActivityRef.get(), true);
			if (succeed) {
				if (statusBarColor != INVALID_COLOR) {
					BarUtils.setStatusBarColor(mActivityRef.get(), statusBarColor);
				}
			}
		}
		return this;
	}

	@Override
	public IBar safeDarkFont() {
		return safeDarkFont(INVALID_COLOR);
	}

	@Override
	public IBar safeDarkFontRes(int statusBarColorRes) {
		if (isValid()) {
			boolean succeed = BarUtils.setStatusBarDarkFont(mActivityRef.get(), true);
			if (succeed) {
				BarUtils.setStatusBarColor(mActivityRef.get(), ContextCompat.getColor(mActivityRef.get(), statusBarColorRes));
			}
		}
		return this;
	}

	@Override
	public IBar safeLightFont(int statusBarColor) {
		if (isValid()) {
			boolean succeed = BarUtils.setStatusBarDarkFont(mActivityRef.get(), false);
			if (succeed) {
				if (statusBarColor != INVALID_COLOR) {
					BarUtils.setStatusBarColor(mActivityRef.get(), statusBarColor);
				}
			}
		}
		return this;
	}

	@Override
	public IBar safeLightFont() {
		return safeLightFont(INVALID_COLOR);
	}

	@Override
	public IBar safeLightFontRes(int statusBarColorRes) {
		if (isValid()) {
			boolean succeed = BarUtils.setStatusBarDarkFont(mActivityRef.get(), false);
			if (succeed) {
				BarUtils.setStatusBarColor(mActivityRef.get(), ContextCompat.getColor(mActivityRef.get(), statusBarColorRes));
			}
		}
		return this;
	}
}
