package org.footoo.cjflsq.neck.system;

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
	    mTimeServiceHandler.sendMessageDelayed(mTimeServiceHandler.obtainMessage(), 10000);
	    Toast.makeText(getApplicationContext(), "哥么儿，时间到了", Toast.LENGTH_SHORT).show();
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
		
	mTimeServiceHandler.sendMessageDelayed(msg, 5000);

	return START_REDELIVER_INTENT;
    }
    
    public IBinder onBind(Intent intent) {
	return null;
    }

    public void onDestroy() {
	thread.quit();
    }
}
