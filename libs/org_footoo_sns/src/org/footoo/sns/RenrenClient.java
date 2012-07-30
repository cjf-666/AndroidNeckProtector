package org.footoo.sns;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.exception.RenrenAuthError;
import com.renren.api.connect.android.status.StatusSetRequestParam;
import com.renren.api.connect.android.view.RenrenAuthListener;

public class RenrenClient {
	private final static String APP_KEY = "af14302a5dcb4db9968c75553f0b9ff8";
	private final static String APP_SECRET = "f801a311a6f344a5a580401a6c567cbd";
	private final static String APP_ID= "205659";
	private Activity activity;
	private ProgressDialog mpDialog;
	private Handler handler = new Handler(){
		@Override
    	public void handleMessage(Message msg){
			if (msg.what == 0)
			{
				Toast.makeText(activity.getApplicationContext(), "发送成功", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(activity.getApplicationContext(), "发送失败", Toast.LENGTH_LONG).show();
			}
			
		}};

	public void publishStatus(Activity activity, String text)
	{
		Renren renren = new Renren(APP_KEY, APP_SECRET, APP_ID, activity.getApplicationContext()); 
		this.activity = activity;
		renren.authorize(activity, new RenrenWebAuthListener(renren, text));
		
	}
	
	public class RenrenWebAuthListener implements RenrenAuthListener {
		private String status;
		private Renren renren;
		
		public RenrenWebAuthListener(Renren renren, String Text)
		{
			this.renren = renren;
			this.status = Text;
		}
		public void onComplete(Bundle values) {
			final StatusSetRequestParam param = new StatusSetRequestParam(status);
			mpDialog = new ProgressDialog(activity);
			mpDialog.setTitle("分享到人人网");//设置标题
			mpDialog.setMessage("正在发布，请稍后…"); 
			mpDialog.show();
				Thread t = new Thread() {  
                    public void run(){  
            			try {
            				renren.publishStatus(param);
            				mpDialog.cancel(); 
            				handler.sendEmptyMessage(0);
            				activity.finish();
            			} catch (Throwable e) {
            				mpDialog.cancel(); 
            				handler.sendEmptyMessage(1);
            			}
                    }  
                };
                t.start();
		}

		public void onCancelLogin() {
		
		}

		public void onCancelAuth(Bundle values) {
		}

		public void onRenrenAuthError(RenrenAuthError renrenAuthError) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
}


