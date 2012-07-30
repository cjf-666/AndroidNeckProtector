package org.footoo.sns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public class SinaMicroBlogClient {
	private static final String CONSUMER_KEY = "2685797514";
	private static final String CONSUMER_SECRET = "ae4de89d059ae4c3be4fe4f1d75ebb18";
	
	public void feed(Activity activity, String text)
	{
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
		weibo.setRedirectUrl("http://org.footoo.cjflsq/android-callback-page");
		weibo.authorize(activity, new AuthDialogListener(activity, text));
	}
	
	class AuthDialogListener implements WeiboDialogListener {
		private String status;
		private Activity activity;
		
		public AuthDialogListener(Activity activity, String Text)
		{
			this.status = Text;
			this.activity = activity;
		}
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");

			AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);
			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);
			WeiboParameters bundle = new WeiboParameters();
	        bundle.add("source", CONSUMER_KEY);
	        bundle.add("status", status);
	        String url = Weibo.SERVER + "statuses/update.json";
	        
	        Weibo weibo = Weibo.getInstance();
	        try {
				weibo.request(activity, url, bundle, Utility.HTTPMETHOD_POST, weibo.getAccessToken());
				Toast.makeText(activity.getApplicationContext(), "发布成功", Toast.LENGTH_LONG).show();
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(activity.getApplicationContext(), "发布失败", Toast.LENGTH_LONG).show();
			}
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

