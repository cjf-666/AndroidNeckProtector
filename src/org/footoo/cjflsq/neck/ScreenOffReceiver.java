package org.footoo.cjflsq.neck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOffReceiver extends BroadcastReceiver {
   public void onReceive(Context context, Intent intent) {
       Intent stopIntent = new Intent(context, TimeService.class);
       context.stopService(stopIntent);
   }
}