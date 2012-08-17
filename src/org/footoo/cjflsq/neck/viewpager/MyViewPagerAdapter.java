package org.footoo.cjflsq.neck.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
	InfoFragment infoFragment = new InfoFragment();

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
			return new InfoFragment();
		case 2:
			return new InfoFragment();
		}
		return new InfoFragment();
	}
}