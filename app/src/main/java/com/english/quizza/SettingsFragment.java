package com.english.quizza;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Get the preference category by key
        PreferenceCategory expandableCategory = findPreference("expandable");

        // Use PreferenceCategory instead of PreferenceScreen to cast the preference fragment
        if (expandableCategory != null) {
            SeekBarPreference seekBarPreference = expandableCategory.findPreference("seekbar_preference");
            Preference wallpaperPreference = expandableCategory.findPreference("intent_preference");
            ListPreference listPreference = expandableCategory.findPreference("list_preference");
        }
    }
}

