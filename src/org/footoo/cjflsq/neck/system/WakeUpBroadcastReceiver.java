package org.footoo.cjflsq.neck.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.widget.Toast;

public class WakeUpBroadcastReceiver extends BroadcastReceiver {

    static private Time startTime = new Time();

    public void onReceive(Context context, Intent intent) {
        startTime.setToNow();
        //DataManager.getInstance().submitStartTime(startTime);
        //ScoreManager.getInstance().submitStartTime(startTime);

        Toast.makeText(context.getApplicationContext(), "屏幕解锁", Toast.LENGTH_SHORT).show();

        Intent timeServiceIntent = new Intent(context, TimeService.class);
        context.startService(timeServiceIntent);
    }
}
