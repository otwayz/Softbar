package com.otway.library;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Otway on 2018/1/15.
 */
class BarCompat implements IBar {

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
}
