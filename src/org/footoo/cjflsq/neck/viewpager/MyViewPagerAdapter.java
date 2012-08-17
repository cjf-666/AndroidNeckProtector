package org.footoo.cjflsq.neck.viewpager;

import org.footoo.cjflsq.neck.settings.SettingsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    InfoFragment infoFragment = new InfoFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    
    public MyViewPagerAdapter(FragmentManager fm) {
	super(fm);
	// TODO Auto-generated constructor stub
    }
    
    @Override
	public int getCount() {
	return 3;
    }
    
    @Override
	public Fragment getItem(int arg0) {
	// TODO Auto-generated method stub
	switch (arg0) {
	case 0:
	    return infoFragment;
	case 1:
	    return (Fragment)settingsFragment;
	case 2:
	    return new InfoFragment();
	}
	return new InfoFragment();
    }
}