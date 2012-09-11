package org.footoo.cjflsq.neck.sns;

import org.footoo.cjflsq.neck.system.TimeService;
import org.footoo.cjflsq.neck.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class DialogActivity extends Activity {
    
    private Button quitButton;
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_share_layout);

	Button sndBtn = (Button) findViewById(R.id.btnSend);
	sndBtn.setOnClickListener(new SendOnClickListener());
    }
    
    private class SendOnClickListener implements OnClickListener {
	@Override
	public void onClick(View v) {
	    SNSSupport.shareToRenren(DialogActivity.this, "cjf和lsq是好基友", null);
	}
    }
}
