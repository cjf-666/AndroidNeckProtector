package org.footoo.cjflsq.neck.userscore;

import org.footoo.cjflsq.neck.MyApplication;
import org.footoo.cjflsq.neck.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.Time;
import android.util.Log;

public class ScoreManager {
	
    private int score;	
    private SharedPreferences mSharedPreferences;	
    private Editor mEditor;
    private String scoreCate;
    private static ScoreManager instance = new ScoreManager();
    private Time startTime;
    private int factor = 1;

    public static ScoreManager getInstance() {
	return instance;
    }
    
    private ScoreManager() {
	scoreCate = MyApplication.getAppContext().getString(R.string.everyday_score);
	mSharedPreferences = MyApplication.getAppContext().getSharedPreferences(MyApplication.getAppContext().getString(R.string.score_filename), Context.MODE_PRIVATE);
	mEditor = mSharedPreferences.edit();
	score = mSharedPreferences.getInt(scoreCate, 100);
    }
    
    public void submitEndTime(Time endTime) {
	int duration = (int) ((endTime.toMillis(false) - startTime.toMillis(false)) / 60000);

	if (endTime.yearDay > startTime.yearDay || endTime.year > startTime.year) {
	    Time tmp = new Time(endTime);
	    tmp.hour = 0;
	    tmp.minute = 0;

	    calc(duration - (int) ((tmp.toMillis(false) - startTime.toMillis(false)) / 60000));
	    /*putScoreToDatabase()*/
	    set(100);
	    calc(duration - (int) ((endTime.toMillis(false) - tmp.toMillis(false)) / 60000));
	}     
	else {
	    calc(duration);
	}
    }

    private void calc(int drt) {
	Log.v("caojingfan",new Integer(drt).toString());
	if (drt > 3) {
	    deductScore(min(3, drt - 3) * factor * 2);
	}
	if (drt > 6) {
	    deductScore(min(6, drt - 6) * factor * 3);
	}
	if (drt > 12) {
	    deductScore((drt - 12) * factor * 4);
	}

	if (score < 0) {
	    set(0);
	}
    }

    private int min(int a, int b) {
	if (a < b) {
	    return a;
	}
	else {
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
    }

    private int deductScore(int n){
	score -= n;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
	return score;
    }
	
    private int getScore(){
	return score;
    }
	
    private int rewardScore(int n){
	score += n;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
	return score;
    }
	
    private void set(int s){
	score = s;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
    }
}
