package org.footoo.cjflsq.neck.userscore;

import org.footoo.cjflsq.neck.database.DataManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import org.footoo.cjflsq.neck.MyApplication;
import org.footoo.cjflsq.neck.R;

public class ScoreManager {

    private int score;
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;
    private String scoreCate;
    private static ScoreManager instance = new ScoreManager();
    private Time startTime;
    private Time endTime = new Time();
    private int factor = 1;
    private int millisToMin = 60000;

    public static ScoreManager getInstance() {
        return instance;
    }

    private ScoreManager() {
	scoreCate = MyApplication.getAppContext().getString(R.string.everyday_score);
	mSharedPreferences = MyApplication.getAppContext().getSharedPreferences(MyApplication.getAppContext().getString(R.string.score_filename), Context.MODE_PRIVATE);
	mEditor = mSharedPreferences.edit();
	score = mSharedPreferences.getInt(scoreCate, 99);
	endTime.set(0);
    }
    
    public void submitEndTime(Time enTime) {
	endTime = new Time(enTime);
	int duration = (int) ((endTime.toMillis(false) - startTime.toMillis(false)) / millisToMin);

        if (endTime.yearDay > startTime.yearDay || endTime.year > startTime.year) {
            Time tmp = new Time(endTime);
            tmp.hour = 0;
            tmp.minute = 0;

            calc(duration - (int) ((tmp.toMillis(false) - startTime.toMillis(false)) / millisToMin));
	    if (DataManager.getInstance().getDayTime() / (millisToMin * 60) > 1) {
		deductScore(6);
	    }
	    DataManager.getInstance().putScore(score);
            set(99);
            calc(duration - (int) ((endTime.toMillis(false) - tmp.toMillis(false)) / millisToMin));
        } else {
            calc(duration);
        }
	Log.v("caojingfan", new Integer(duration).toString());
    }

    private void calc(int drt) {
	//Log.v("caojingfan",new Integer(drt).toString());
	if (drt > 5) {
	    deductScore(min(10, drt - 5) * factor * 1);
	}
	if (drt > 15) {
	    deductScore(min(15, drt - 15) * factor * 2);
	}
	if (drt > 30) {
	    deductScore((drt - 30) * factor * 3);
	}

	if (score < 0) {
	    set(0);
	}

    }

    private int min(int a, int b) {
	if (a < b) {
	    return a;
	} else {
	    return b;
	}
    }

    public void submitStartTime(Time stTime) {
	startTime = new Time(stTime);
	if (startTime.hour >= 23 || startTime.hour <= 7 || startTime.hour >= 12 && startTime.hour <= 14) {
	    factor = 2;
	}
	else {
	    factor = 1;
	}
	
	if (endTime.toMillis(false) != 0 && (startTime.yearDay > endTime.yearDay || startTime.year > endTime.year)) {
	     if (DataManager.getInstance().getDayTime() / (millisToMin * 60) > 1) {
		deductScore(6);
	    }
	    DataManager.getInstance().putScore(score);
	    set(99);
	}
    }

    private int deductScore(int n) {
	score -= n;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
	return score;
    }

    public int getScore() {
	return score;
    }

    public int rewardScore(int n) {
	score += n;
	if (score > 99) {
	    set(99);
	}
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
	return score;
    }

    private void set(int s) {
	score = s;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
    }
}
