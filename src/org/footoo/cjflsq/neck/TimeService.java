package org.footoo.cjflsq.neck;

import android.os.HandlerThread;
import android.os.Looper;
import android.app.Service;
import android.widget.Toast;
import android.content.Context;
import android.os.Process;
import android.os.Message;
import android.os.Handler;
import android.content.Intent;
import android.os.IBinder;

public class TimeService extends Service {
    private Looper mTimeServiceLooper;
    private TimeServiceHandler mTimeServiceHandler;

    private final class TimeServiceHandler extends Handler {
	public TimeServiceHandler (Looper looper) {
	    super(looper);
	}
        public void handleMessage(Message msg) {
	    Toast.makeText(getApplicationContext(), "哥么儿，时间到了", Toast.LENGTH_SHORT).show();
	}
    }

    public void onCreate() {
	HandlerThread thread = new HandlerThread("TimeThread", Process.THREAD_PRIORITY_BACKGROUND);
	thread.start();
	
	mTimeServiceLooper = thread.getLooper();
	mTimeServiceHandler = new TimeServiceHandler(mTimeServiceLooper);
    }
    
    public int onStartCommand(Intent intent, int flags, int startID) {
	Message msg = mTimeServiceHandler.obtainMessage();
	
	mTimeServiceHandler.sendMessageDelayed(msg, 10000);

	return START_REDELIVER_INTENT;
    }
    
    public IBinder onBind(Intent intent) {
	return null;
    }

    public void onDestroy() {
    }
}
