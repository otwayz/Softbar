/*
 * Copyright 2018 Otway
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
