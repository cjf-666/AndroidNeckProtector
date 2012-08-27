package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.settings.SettingsActivity;
import org.footoo.cjflsq.neck.cervicaltest.CervicalTestActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
        setContentView(R.layout.activity_main);

	Button settingButton = (Button) findViewById(R.id.settings_button);
	settingButton.setOnClickListener(new SettingOnClickListener());
	
	Button testButton = (Button) findViewById(R.id.start_test_button);
	testButton.setOnClickListener(new TestOnClickListener());
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