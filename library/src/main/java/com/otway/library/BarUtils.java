/*
 * Copyright 2017 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.otway.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;


class BarUtils {
	private final static String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
	private static final String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
	private static String sMiuiVersionName;
	private static String sFlymeVersionName;

	static {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
			Properties properties = new Properties();
			properties.load(fileInputStream);
			Class<?> clzSystemProperties = Class.forName("android.os.SystemProperties");
			Method getMethod = clzSystemProperties.getDeclaredMethod("get", String.class);
			// miui
			sMiuiVersionName = getLowerCaseName(properties, getMethod, KEY_MIUI_VERSION_NAME);
			//flyme
			sFlymeVersionName = getLowerCaseName(properties, getMethod, KEY_FLYME_VERSION_NAME);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Set the status bar color.
	 */
	public static void setStatusBarColor(Activity activity, int statusBarColor) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(statusBarColor);
		}
	}

	/**
	 * Set the navigation bar color.
	 */
	public static void setNavigationBarColor(Activity activity, int navigationBarColor) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setNavigationBarColor(navigationBarColor);
		}
	}

	/**
	 * Set the content layout full the StatusBar, but do not hide StatusBar.
	 */
	public static void invasionStatusBar(Activity activity) {
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
	 */
	public static void invasionNavigationBar(Activity activity) {
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
	 */
	public static void setStatusBarDarkFont(Activity activity, boolean darkFont) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			setDefaultStatusBarFont(activity, darkFont);
		} else if (isMeizu()) {
			setMeizuStatusBarFont(activity, darkFont);
		} else if (isXiaomi()) {
			setMIUIStatusBarFont(activity, darkFont);
		}
	}

	/**
	 * Set offset view
	 */
	public static void needOffsetView(Activity activity, View needOffsetView) {
		if (needOffsetView != null) {
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
			layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity),
					layoutParams.rightMargin, layoutParams.bottomMargin);
		}
	}

	private static int getStatusBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}


	//**---------------------------MeiZu &  XiaoMi-------------------------------------

	private static void setMeizuStatusBarFont(Activity activity, boolean darkFont) {
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
		}
	}

	private static void setMIUIStatusBarFont(Activity activity, boolean dark) {
		Window window = activity.getWindow();
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
		}
	}

	private static void setDefaultStatusBarFont(Activity activity, boolean dark) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			Window window = activity.getWindow();
			View decorView = window.getDecorView();
			if (dark) {
				decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			} else {
				decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}
		}
	}

	static boolean isSupportedStatusModeChange() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ||
				(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && (isMeizu() || isXiaomi()));
	}

	static boolean isFullScreen(Activity activity) {
		return (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
	}

	private final static String MEIZUBOARD[] = {"m9", "M9", "mx", "MX"};
	private final static String FLYME = "flyme";


	private static boolean isMeizu() {
		return isPhone(MEIZUBOARD) || isFlyme();
	}

	private static boolean isXiaomi() {
		return Build.BRAND.toLowerCase().contains("xiaomi");
	}


	private static boolean isPhone(String[] boards) {
		final String board = android.os.Build.BOARD;
		if (board == null) {
			return false;
		}
		for (String board1 : boards) {
			if (board.equals(board1)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isFlyme() {
		return !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName.contains(FLYME);
	}

	@Nullable
	private static String getLowerCaseName(Properties p, Method get, String key) {
		String name = p.getProperty(key);
		if (name == null) {
			try {
				name = (String) get.invoke(null, key);
			} catch (Exception ignored) {
			}
		}
		if (name != null) name = name.toLowerCase();
		return name;
	}
}