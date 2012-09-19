package org.footoo.cjflsq.neck;
  
import android.app.Activity;  
import android.os.Bundle;  
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
public class KNActivity extends Activity {  

    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.knowledge_layout);  

	Button returnBtn = (Button) findViewById(R.id.kn_return_button);
	returnBtn.setOnClickListener(new ReturnOnClickListener());
    }

    private class ReturnOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View v) {
	    Intent intent = new Intent(KNActivity.this, MainActivity.class);
	    startActivity(intent);
	    KNActivity.this.finish();
	}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode == KeyEvent.KEYCODE_BACK){
	    Intent intent = new Intent(KNActivity.this, MainActivity.class);
	    startActivity(intent);
	    KNActivity.this.finish();
	}
	return super.onKeyDown(keyCode, event);
    }	
}