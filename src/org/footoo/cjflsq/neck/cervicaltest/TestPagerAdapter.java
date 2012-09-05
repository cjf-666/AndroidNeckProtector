package org.footoo.cjflsq.neck.cervicaltest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TestPagerAdapter extends FragmentPagerAdapter {
    
    public static int PAGER_COUNT = 30;
    
    public TestPagerAdapter(FragmentManager fm) {
	super(fm);
    }
    
    @Override
    public int getCount() {
	return PAGER_COUNT;
    }
    
    @Override
    public Fragment getItem(int arg0) {
	// TODO Auto-generated method stub
	return (new IssueFragment(arg0));
    }
}