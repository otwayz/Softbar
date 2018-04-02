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
