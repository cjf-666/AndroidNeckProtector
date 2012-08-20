package org.footoo.cjflsq.neck.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment{
	org.footoo.cjflsq.neck.settings.SettingsFragment settingsFragment = new org.footoo.cjflsq.neck.settings.SettingsFragment();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return settingsFragment.getView();
		
	}
}
