package org.footoo.cjflsq.neck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ExplainActivity extends Activity {
    @Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.explain_activity);
	
	Button rtBtn = (Button) findViewById(R.id.exp_return_button);
	rtBtn.setOnClickListener(new ReturnOnClickListener());
    }

    private class ReturnOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View v) {
	    ExplainActivity.this.finish();
	}
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    ExplainActivity.this.finish();
	}
	return super.onKeyDown(keyCode, event);
    }
}