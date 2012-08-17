package org.footoo.cjflsq.neck.settings;

import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {
    @override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.preferences);
    }
}