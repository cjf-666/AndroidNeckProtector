package org.footoo.cjflsq.neck.viewpager;

import org.footoo.cjflsq.neck.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyViewPagerAdapter extends PagerAdapter { 
	
	
@Override
public int getCount() {
    return 3;
}

@Override
public Object instantiateItem(final View collection, final int position) {
    View v = new View(collection.getContext());
    LayoutInflater inflater =
            (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    switch (position) {
    case 0:
        v = inflater.inflate(R.layout.info, null, false);
        final Button btn = (Button) v.findViewById(R.id.button1);
        btn.setOnClickListener( new OnClickListener() {
            public void onClick(View m) {
               Toast.makeText(collection.getContext(),"click",Toast.LENGTH_LONG).show();
            }
        });
        break;
    case 1:
    	v = inflater.inflate(R.layout.settings, null, false);
        break;
    case 2:
    	v = inflater.inflate(R.layout.settings, null, false);
        break;
    }

    ((ViewPager)collection).addView(v, 0);

    return v;
}

@Override
public void destroyItem(final View arg0, final int arg1, final Object arg2) {
    ((ViewPager) arg0).removeView((View) arg2);

}

@Override
public boolean isViewFromObject(final View arg0, final Object arg1) {
    return arg0 == ((View) arg1);

}

}