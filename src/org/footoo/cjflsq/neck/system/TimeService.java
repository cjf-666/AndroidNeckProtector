package org.footoo.cjflsq.neck.system;

import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.*;
import android.os.Process;
import org.footoo.cjflsq.neck.MainActivity;
import org.footoo.cjflsq.neck.MyApplication;
import org.footoo.cjflsq.neck.R;

public class TimeService extends Service {
    private Looper mTimeServiceLooper;
    private TimeServiceHandler mTimeServiceHandler;
    private HandlerThread thread = new HandlerThread("TimeThread", Process.THREAD_PRIORITY_BACKGROUND);
    private int intervalTime;
    private NotificationManager mNotificationManager;
    private CharSequence tickerText;
    private Notification mNotification;
    private int iconId;
    private Context mContext;
    private CharSequence contentTitle;
    private CharSequence contentText;
    private PendingIntent contentIntent;
    private Vibrator mVibrator;
    private ScreenOffReceiver mScreenOffReceiver;

    private final class TimeServiceHandler extends Handler {
        public TimeServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            startNotification();
            ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            ComponentName mComponentName = mActivityManager.getRunningTasks(1).get(0).topActivity;
            if (!mComponentName.getClassName().equals(MainActivity.class.getName())) {
                mVibrator.vibrate(700);
            }
            sendMsgDld();
        }
    }

    public void onCreate() {
        thread.start();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        mScreenOffReceiver = new ScreenOffReceiver();
        getApplicationContext().registerReceiver(mScreenOffReceiver, filter);

        mTimeServiceLooper = thread.getLooper();
        mTimeServiceHandler = new TimeServiceHandler(mTimeServiceLooper);
        initNotification();
        mVibrator = (Vibrator) MyApplication.getAppContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startID) {
        sendMsgDld();
        return START_REDELIVER_INTENT;
    }

    private void sendMsgDld() {
        Message msg = mTimeServiceHandler.obtainMessage();
        msg.what = 1;

        intervalTime = getSharedPreferences(getString(R.string.settings_preferences_filename).toString(), 0).getInt(getString(R.string.pref_key_time).toString(), 5);
        mTimeServiceHandler.sendMessageDelayed(msg, intervalTime * 1000);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        getApplicationContext().unregisterReceiver(mScreenOffReceiver);
        thread.quit();
    }

    private void initNotification() {
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) getSystemService(ns);
        iconId = R.drawable.umeng_share_logo_renren;
        tickerText = getString(R.string.notification_ticker_text).toString();

        mContext = MyApplication.getAppContext();
        contentTitle = getString(R.string.notification_content_title).toString();
        contentText = getString(R.string.notification_content_text).toString();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    }

    private void startNotification() {
        long when = System.currentTimeMillis();
        mNotification = new Notification(iconId, tickerText, when);
        mNotification.setLatestEventInfo(mContext, contentTitle, contentText, contentIntent);
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(1, mNotification);
    }
}
