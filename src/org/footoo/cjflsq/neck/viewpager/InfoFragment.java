package org.footoo.cjflsq.neck.viewpager;

import org.footoo.cjflsq.neck.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
	//Inflate the layout for this fragment
		View v =  inflater.inflate(R.layout.info, container, false);
		return v;
	}
}
