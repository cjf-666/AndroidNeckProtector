package org.footoo.cjflsq.neck.sns;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public class SinaMicroBlogClient {
	private static final String APP_KEY = "2685797514";
	private static final String APP_SECRET = "ae4de89d059ae4c3be4fe4f1d75ebb18";
	private String status;
	private ProgressDialog mpDialog;
	private Activity activity;
	private PublishCompleteListener mListener;
	
	
	private String token;
	private String expires_in;
	private AccessToken accessToken;
		
	public void publishStatus(Activity activity, String text, PublishCompleteListener listener)
	{
		this.activity = activity;
		status = text;
		mListener = listener;
		Weibo weibo = Weibo.getInstance();
		if (!weibo.isSessionValid())
		{
			weibo.setupConsumerConfig(APP_KEY, APP_SECRET);
			weibo.setRedirectUrl("http://org.footoo.cjflsq/android-callback-page");
			weibo.authorize(activity, new AuthDialogListener());
		}else{
			publishStatus();
		}
	}
	
	private void publishStatus(){
		 mpDialog = new ProgressDialog(activity);
			mpDialog.setTitle("分享到新浪微博");//设置标题
			mpDialog.setMessage("正在发布，请稍后…"); 
			mpDialog.show();
				Thread t = new Thread() {  
                 public void run(){  
                 	 try {
              			accessToken = new AccessToken(token, APP_SECRET);
              			accessToken.setExpiresIn(expires_in);
              			Weibo.getInstance().setAccessToken(accessToken);
              			final WeiboParameters bundle = new WeiboParameters();
              	        bundle.add("source", APP_KEY);
              	        bundle.add("status", status);
              	        final String url = Weibo.SERVER + "statuses/update.json";
                 		Weibo weibo = Weibo.getInstance();
          				weibo.request(activity, url, bundle, Utility.HTTPMETHOD_POST, weibo.getAccessToken());
           				mpDialog.cancel(); 
           				if (mListener != null)
           					mListener.publishComplete(PublishCompleteListener.PUBLISH_SUCCESS);
                 	 } catch (WeiboException e) {
          				// TODO Auto-generated catch block
                 		Log.e("err",e.toString());
                 		mpDialog.cancel(); 
                 		if (mListener != null)
                 			mListener.publishComplete(PublishCompleteListener.PUBLISH_FAILED);
          			}
                 }  
             };
             t.start();
	}
	
	class AuthDialogListener implements WeiboDialogListener {

		public void onComplete(Bundle values) {
     		token = values.getString("access_token");
  			expires_in = values.getString("expires_in");
	        publishStatus();
		}

		public void onError(DialogError e) {
			Log.e("SinaMicroBlogClient", "Auth error : " + e.getMessage());
		}

		public void onCancel() {
			Log.w("SinaMicroBlogClient", "Auth canceled.");
		}

		public void onWeiboException(WeiboException e) {
			Log.e("SinaMicroBlogClient", "Auth exception : " + e.getMessage());
		}

	}
}


