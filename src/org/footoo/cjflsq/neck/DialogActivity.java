package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.system.TimeService;

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
        setContentView(R.layout.dialog_activity_layout);

	quitButton = (Button) findViewById(R.id.quit_button);
	quitButton.setOnClickListener(new OnClickListener() {
		public void onClick(View view) {
		    Intent mDialogIntent = new Intent(DialogActivity.this, TimeService.class);
		    startService(mDialogIntent);
		    finish();
		}
	    });
    }
}
