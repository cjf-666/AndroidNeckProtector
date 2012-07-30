package org.footoo.sns;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SNSSupport {
	
    public static void Share(final Activity activity, final String title, final String msg) {
    		Intent intent = new Intent(Intent.ACTION_SEND);
    		intent.setType("text/plain");
    		intent.putExtra(Intent.EXTRA_SUBJECT, title);
    		intent.putExtra(Intent.EXTRA_TEXT, msg);
    		ShareAdapter mAdapter = new ShareAdapter(activity, intent);
    		Builder ad = new AlertDialog.Builder(activity);
    		ad.setTitle("分享列表");
    		View view = activity.getLayoutInflater().inflate(R.layout.share_dialog_view, null);
    		ListView listView = (ListView)view.findViewById(R.id.myListView);
    		listView.setCacheColorHint(0);
    		listView.setAdapter(mAdapter);
    		ad.setView(view);
    		final AlertDialog a = ad.create();
    		listView.setOnItemClickListener(new OnItemClickListener(){
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				ShareAdapter sa = (ShareAdapter)arg0.getAdapter();
    				a.dismiss();
    				activity.startActivity(sa.getIntentForPosition(arg2));
    			}
        		});
    		a.show();
		}
}
