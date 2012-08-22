package org.footoo.cjflsq.neck.system;

import org.footoo.cjflsq.neck.DialogActivity;
import org.footoo.cjflsq.neck.R;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

public class TimeService extends Service {
    private Looper mTimeServiceLooper;
    private TimeServiceHandler mTimeServiceHandler;
    private HandlerThread thread = new HandlerThread("TimeThread", Process.THREAD_PRIORITY_BACKGROUND);

    private final class TimeServiceHandler extends Handler {
	public TimeServiceHandler (Looper looper) {
	    super(looper);
	}
        public void handleMessage(Message msg) {	    
	    Intent mDialogIntent = new Intent(TimeService.this,DialogActivity.class); 
	    mDialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(mDialogIntent);
	}
    }

    public void onCreate() {
	thread.start();
	
	IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
	ScreenOffReceiver mScreenOffReceiver = new ScreenOffReceiver();
	getApplicationContext().registerReceiver(mScreenOffReceiver, filter);

	mTimeServiceLooper = thread.getLooper();
	mTimeServiceHandler = new TimeServiceHandler(mTimeServiceLooper);
    }
    
    public int onStartCommand(Intent intent, int flags, int startID) {
	Message msg = mTimeServiceHandler.obtainMessage();
	
	int intervalTime = getSharedPreferences("org.footoo.cjflsq.neck_preferences", 0).getInt(getString(R.string.pref_key_time).toString(), 5);
	mTimeServiceHandler.sendMessageDelayed(msg, intervalTime*1000);

	return START_REDELIVER_INTENT;
    }
    
    public IBinder onBind(Intent intent) {
	return null;
    }

    public void onDestroy() {
	thread.quit();
    }
}
