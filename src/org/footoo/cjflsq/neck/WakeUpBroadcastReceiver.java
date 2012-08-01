package org.footoo.cjflsq.neck;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

public class WakeUpBroadcastReceiver extends BroadcastReceiver {
    
    public void onReceive(Context context, Intent intent) {
	Toast.makeText(context.getApplicationContext(),"shoudao", Toast.LENGTH_SHORT).show();
	Intent timeServiceIntent = new Intent(context, TimeService.class);
	context.startService(timeServiceIntent);
    }
}