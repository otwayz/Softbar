package com.otway.library;

import android.app.Activity;
import android.util.SparseArray;

/**
 * Created by Otway on 2018/1/15.
 */

public class SoftBar {
	private static SparseArray<BarCompat> mBarCompatMap = new SparseArray<>();

	public static IBar with(Activity activity) {
		BarCompat barCompat = mBarCompatMap.get(System.identityHashCode(activity));
		if (barCompat == null) {
			barCompat = new BarCompat(activity);
			mBarCompatMap.put(System.identityHashCode(activity), barCompat);
		}
		return barCompat;
	}

	public static void release(Activity activity) {
		mBarCompatMap.remove(System.identityHashCode(activity));
	}
}
