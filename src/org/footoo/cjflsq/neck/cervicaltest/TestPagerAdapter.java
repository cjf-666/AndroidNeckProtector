package org.footoo.cjflsq.neck.viewpager;

import org.footoo.cjflsq.neck.settings.SettingsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TestPagerAdapter extends FragmentPagerAdapter {

    public TestPagerAdapter(FragmentManager fm) {
	super(fm);
    }
    
    @Override
    public int getCount() {
	return 30;
    }
    
    @Override
    public Fragment getItem(int arg0) {
	// TODO Auto-generated method stub
	return (new IssueFragment(arg0));
    }
}