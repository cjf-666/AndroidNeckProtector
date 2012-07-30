package org.footoo.sns;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SNSShareActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_share);
        
        Intent callerIntent = getIntent();
		String text = callerIntent.getStringExtra(Intent.EXTRA_TEXT);
		final String to = callerIntent.getStringExtra("target");
		
		if (to.equals("renren"))
			this.setTitle("分享到人人网");
		else if (to.equals("sina"))
			this.setTitle("分享到新浪微博");
		
        final Button btnBack = (Button)findViewById(R.id.btnBack);
        final Button btnSend = (Button)findViewById(R.id.btnSend);
        final EditText status = (EditText)findViewById(R.id.editText1);
        status.setText(text);
        
        btnBack.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				goBack();
			}
        	
        });
        
        btnSend.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (status.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "发送内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (to.equals("renren"))
				{
					RenrenClient rc = new RenrenClient();
					rc.publishStatus(SNSShareActivity.this, status.getText().toString());
				}
				else if (to.equals("sina"))
				{
					SinaMicroBlogClient smbc = new SinaMicroBlogClient();
					smbc.publishStatus(SNSShareActivity.this, status.getText().toString());
				}
			}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sns_share, menu);
        return true;
    }
    
    public boolean onKeyDown(int keyCode,KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
    		goBack();
    		return true;
    	}
    	return false;
    }
    
    private void goBack(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("分享")
        .setMessage("确定要取消发布？")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            	dialog.dismiss();
                finish();
            }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //flag = false;
            	dialog.dismiss();
            }
        }).setCancelable(false);//是否能被BLACK键取消
        builder.show();
    }
}
