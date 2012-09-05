package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.settings.SettingsActivity;
import org.footoo.cjflsq.neck.cervicaltest.CervicalTestActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Handler;

import android.widget.Toast;
import java.lang.Integer;

public class MainActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
        setContentView(R.layout.activity_main);

	Button settingButton = (Button) findViewById(R.id.settings_button);
	settingButton.setOnClickListener(new SettingOnClickListener());
	
	Button testButton = (Button) findViewById(R.id.start_test_button);
	testButton.setOnClickListener(new TestOnClickListener());

	presentScore();
    }

    @Override
    public void onResume() {
	super.onResume();
	presentScore();
    }

    private void presentScore() {
	int mScore;
	ProgressBar mProgressBar;
	ImageView mImageView;
	mScore = getSharedPreferences(getString(R.string.score_filename).toString(), 0).getInt(getString(R.string.cervical_test_score).toString(), 100);

	mProgressBar = (ProgressBar) findViewById(R.id.score_progressbar);
	mProgressBar.setProgress(mScore);
	
	mImageView = (ImageView) findViewById(R.id.score_image_2);
	setImageView(mImageView, mScore % 10);
	mScore /= 10;
	mImageView = (ImageView) findViewById(R.id.score_image_1);
	setImageView(mImageView, mScore % 10);
	mScore /= 10;
	mImageView = (ImageView) findViewById(R.id.score_image_0);
	setImageView(mImageView, mScore % 10);
    }

    private void setImageView(ImageView imv, int num) {
	switch (num) {
	case 0:
	    imv.setImageResource(R.drawable.rating_number_0);
	    break;
	case 1:
	    imv.setImageResource(R.drawable.rating_number_1);
	    break;
	case 2:
	    imv.setImageResource(R.drawable.rating_number_2);
	    break;
	case 3:
	    imv.setImageResource(R.drawable.rating_number_3);
	    break;
	case 4:
	    imv.setImageResource(R.drawable.rating_number_4);
	    break;
	case 5:
	    imv.setImageResource(R.drawable.rating_number_5);
	    break;
	case 6:
	    imv.setImageResource(R.drawable.rating_number_6);
	    break;
	case 7:
	    imv.setImageResource(R.drawable.rating_number_7);
	    break;
	case 8:
	    imv.setImageResource(R.drawable.rating_number_8);
	    break;
	case 9:
	    imv.setImageResource(R.drawable.rating_number_9);
	    break;
	}
    }

    private class SettingOnClickListener implements OnClickListener {
	@Override
	public void onClick(View v) {
	    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
	    startActivity(intent);
	}
    }
    private class TestOnClickListener implements OnClickListener {
	@Override
        public void onClick(View v) {
	    Intent intent = new Intent(MainActivity.this, CervicalTestActivity.class);
	    startActivity(intent);
	}
    }
}