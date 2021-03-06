/*
 * Copyright (C) 2012 The CyanogenMod Project
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

package com.cyanogenmod.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;

public class USBMode extends ListPreference implements OnPreferenceChangeListener {

    private static final String FILE_USB_MODE = "/sys/bus/platform/drivers/usb20_otg/force_usb_mode";

    public USBMode(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnPreferenceChangeListener(this);
    }

    public static boolean isSupported() {
        return Utils.fileExists(FILE_USB_MODE);
    }

    /**
     * Restore hspa setting from SharedPreferences. (Write to kernel.)
     * @param context       The context to read the SharedPreferences from
     */
    public static void restore(Context context) {
        if (!isSupported()) {
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Utils.writeValue(FILE_USB_MODE, sharedPrefs.getString(DeviceSettings.KEY_USB_MODE, "2"));
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Utils.writeValue(FILE_USB_MODE, (String) newValue);
        return true;
    }
}
