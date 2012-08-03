package org.footoo.cjflsq.neck;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
import android.app.IntentService;
import android.widget.Toast;
import android.text.format.Time;
import org.footoo.cjflsq.neck.database.DataManager;

public class WakeUpBroadcastReceiver extends BroadcastReceiver {
    
    static private Time startTime =  new Time();
    
    public void onReceive(Context context, Intent intent) {
	startTime.setToNow();
	DataManager.getInstance().submitStartTime(startTime);

	Toast.makeText(context.getApplicationContext(), "屏幕解锁", Toast.LENGTH_SHORT).show();

	Intent timeServiceIntent = new Intent(context, TimeService.class);
	context.startService(timeServiceIntent);
    }
}