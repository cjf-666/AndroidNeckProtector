package org.footoo.cjflsq.neck.system;

import org.footoo.cjflsq.neck.database.DataManager;
import org.footoo.cjflsq.neck.userscore.ScoreManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;

public class ScreenOffReceiver extends BroadcastReceiver {
    static private Time endTime = new Time();
    public void onReceive(Context context, Intent intent) {
	endTime.setToNow();
	//DataManager.getInstance().submitEndTime(endTime);
	//ScoreManager.getInstance().submitEndTime(endTime);
	
	Intent stopIntent = new Intent(context, TimeService.class);
	context.stopService(stopIntent);

	Log.v("caojingfan", "ScreenOffffffffffffffffffffffffffffffffff");
    }
}