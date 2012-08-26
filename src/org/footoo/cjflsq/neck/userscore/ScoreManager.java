package org.footoo.cjflsq.neck.userscore;

import org.footoo.cjflsq.neck.MyApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ScoreManager {
	
    private int score;
	
    private SharedPreferences mSharedPreferences;
	
    private Editor mEditor;

    private String scoreCate;
	
    public ScoreManager(String scoreType) {
	mSharedPreferences = MyApplication.getAppContext().getSharedPreferences(scoreType, Context.MODE_PRIVATE);
	mEditor = mSharedPreferences.edit();
	scoreCate = scoreType;
	score = mSharedPreferences.getInt(scoreCate, 100);
    }
	
    public int deductScore(int n){
	score -= n;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
	return score;
    }
	
    public int getScore(){
	return score;
    }
	
    public int rewardScore(int n){
	score += n;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
	return score;
    }
	
    public void reset(){
	score = 100;
	mEditor.putInt(scoreCate, score);
	mEditor.commit();
    }
}
