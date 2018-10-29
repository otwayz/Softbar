package com.otway.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BarUtils {
	/**
	 * Set the status bar color.
	 * @param activity NonNull
	 * @param statusBarColor ColorInt
	 */
	public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusBarColor) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(statusBarColor);
		}
	}

	/**
	 * Set the navigation bar color.
	 * @param activity NonNull
	 * @param navigationBarColor ColorInt
	 */
	public static void setNavigationBarColor(@NonNull Activity activity,@ColorInt int navigationBarColor) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setNavigationBarColor(navigationBarColor);
		}
	}

	/**
	 * Set the content layout full the StatusBar, but do not hide StatusBar.
	 * @param activity NonNull
	 */
	public static void invasionStatusBar(@NonNull Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			View decorView = window.getDecorView();
			decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		}
	}

	/**
	 * Set the content layout full the NavigationBar, but do not hide NavigationBar.
	 * @param activity NonNull
	 */
	public static void invasionNavigationBar(@NonNull Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			View decorView = window.getDecorView();
			decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setNavigationBarColor(Color.TRANSPARENT);
		}
	}

	/**
	 * Set the status bar to dark.
	 * @param activity NonNull
	 * @param darkFont the word or icon in the status bar area is dark color or not
	 * @return  success or not
	 */
	public static boolean setStatusBarDarkFont(@NonNull Activity activity, boolean darkFont) {
		if (isMeizu()) {
			return setMeizuStatusBarFont(activity, darkFont);
		} else if (isXiaomi()) {
			return setMIUIStatusBarFont(activity, darkFont);
		} else {
			return setDefaultStatusBarFont(activity, darkFont);
		}
	}

	/**
	 * Set offset view
	 * @param activity NonNull
	 * @param needOffsetView the view which need translate Y-Axis
	 */
	public static void needOffsetView(@NonNull Activity activity, View needOffsetView) {
		if (needOffsetView != null) {
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
			layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity),
					layoutParams.rightMargin, layoutParams.bottomMargin);
		}
	}

	private static int getStatusBarHeight(@NonNull Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}


	//**---------------------------MeiZu &  XiaoMi-------------------------------------

	private static boolean setMeizuStatusBarFont(@NonNull Activity activity, boolean darkFont) {
		try {
			WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
			Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
			Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
			darkFlag.setAccessible(true);
			meizuFlags.setAccessible(true);
			int bit = darkFlag.getInt(null);
			int value = meizuFlags.getInt(lp);
			if (darkFont) {
				value |= bit;
			} else {
				value &= ~bit;
			}
			meizuFlags.setInt(lp, value);
			activity.getWindow().setAttributes(lp);
		} catch (Exception ignored) {
			return false;
		}

		return true;
	}

	private static boolean setMIUIStatusBarFont(@NonNull Activity activity, boolean dark) {
		Window window = activity.getWindow();
		View decorView = window.getDecorView();
		Class<?> clazz = window.getClass();

		try {
			Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			int darkModeFlag = field.getInt(layoutParams);
			Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			if (dark) {
				extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
			} else {
				extraFlagField.invoke(window, 0, darkModeFlag);
			}
		} catch (Exception ignored) {
			return false;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (dark) {
				decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			} else {
				decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}
		}
		return true;
	}

	private static boolean setDefaultStatusBarFont(@NonNull Activity activity, boolean dark) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			Window window = activity.getWindow();
			View decorView = window.getDecorView();
			if (dark) {
				decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			} else {
				decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}
			return true;
		}
		return false;
	}

	static boolean isSupportedStatusModeChange() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ||
				(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && (isMeizu() || isXiaomi()));
	}

	static boolean isFullScreen(Activity activity) {
		return (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
	}


	private static boolean isMeizu() {
		return Rom.isFlyme();
	}

	private static boolean isXiaomi() {
		return Rom.isMiui();
	}
}