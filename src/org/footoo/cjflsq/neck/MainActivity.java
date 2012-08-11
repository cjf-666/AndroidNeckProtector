package org.footoo.cjflsq.neck;

import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//使ActionBar变得透明
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// remove the activity title to make space for tabs
		actionBar.setDisplayShowTitleEnabled(false);

		AFragment aFragment = new AFragment(); 
		actionBar.addTab(actionBar.newTab().setText("Tab-A")            
				.setTabListener(new ListenerA(aFragment))); 
		
		BFragment bFragment = new BFragment(); 
		actionBar.addTab(actionBar.newTab().setText("Tab-B")            
				.setTabListener(new ListenerB(bFragment)));
	}
	//点击显示or隐藏ActionBar
	public boolean onTouchEvent(MotionEvent event){
		ActionBar bar = getSupportActionBar();
		switch(event.getAction()){
			case MotionEvent.ACTION_UP:
				if(bar.isShowing()) bar.hide();
				else bar.show();
				break;
			default:
					break;
		}
		return true;
	}
	
	private class ListenerA implements ActionBar.TabListener {
		private AFragment mFragment;
		// Called to create an instance of the listener when adding a new tab
		public ListenerA(AFragment fragment) {
			mFragment = fragment;
		}
		public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		public void onTabUnselected(
				com.actionbarsherlock.app.ActionBar.Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		public void onTabReselected(
				com.actionbarsherlock.app.ActionBar.Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
	}

	private class ListenerB implements ActionBar.TabListener {
		public ListenerB(BFragment fragment) {
		}
		public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		public void onTabUnselected(
				com.actionbarsherlock.app.ActionBar.Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		public void onTabReselected(
				com.actionbarsherlock.app.ActionBar.Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

	}
}