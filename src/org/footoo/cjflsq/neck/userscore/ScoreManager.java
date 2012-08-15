package org.footoo.cjflsq.neck.userscore;

import org.footoo.cjflsq.neck.MyApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ScoreManager {
	
	private static final ScoreManager theSingleton = new ScoreManager();
	
	private int score;
	
	private SharedPreferences sp = MyApplication.getAppContext().getSharedPreferences("score", Context.MODE_PRIVATE);
	
	private Editor editor = sp.edit();
	
	private ScoreManager(){
		score = sp.getInt("score", 100);
	}
	
	public static ScoreManager getInstance(){
		return theSingleton;
	}
	
	public int deductScore(int n){
		score -= n;
		editor.putInt("score", score);
		editor.commit();
		return score;
	}
	
	public int getScore(){
		return score;
	}
	
	public int rewardScore(int n){
		score += n;
		editor.putInt("score", score);
		editor.commit();
		return score;
	}
	
	public void reset(){
		score = 100;
		editor.putInt("score", score);
		editor.commit();
	}
	
}
