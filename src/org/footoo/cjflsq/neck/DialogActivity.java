package org.footoo.cjflsq.neck;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class DialogActivity extends Activity implements OnClickListener {
    
    private Button returnButton;
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity_layout);

	returnButton = (Button) findViewById(R.id.return_button);
	returnButton.setOnClickListener(this);
    }

    public void onClick(View view) {
	finish();
    }
}