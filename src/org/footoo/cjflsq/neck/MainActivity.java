package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.cervicaltest.CervicalTestActivity;
import org.footoo.sns.SNSSupport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
        setContentView(R.layout.activity_main);

	Button settingButton = (Button) findViewById(R.id.settings_button);
	settingButton.setOnClickListener(new SettingOnClickListener());
	
	Button infoButton = (Button) findViewById(R.id.info_button);
	infoButton.setOnClickListener(new InfoOnClickListener());
	
	Button testButton = (Button) findViewById(R.id.start_test_button);
	testButton.setOnClickListener(new TestOnClickListener());
    }

    private class InfoOnClickListener implements OnClickListener {
    	@Override
    	public void onClick(View v) {
    	    Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
    	    startActivity(intent);
    	}
        }
    
    private class SettingOnClickListener implements OnClickListener {
	@Override
	public void onClick(View v) {
	    //Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
	    //startActivity(intent);
		SNSSupport.shareToTencent(MainActivity.this, "ccccccccccccc怎么会不好使", null);
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