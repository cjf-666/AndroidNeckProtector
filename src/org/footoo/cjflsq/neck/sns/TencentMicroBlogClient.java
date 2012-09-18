
package org.footoo.cjflsq.neck.sns;

import android.app.Activity;
import android.util.Log;

import com.tencent.weibo.oauthv1.OAuthV1;

public class TencentMicroBlogClient {
	private final static String APP_KEY = "801203325";	          
	private final static String APP_SECRET = "7c1373f22f63cee29497fa5ee2ed29ae";

	private final static String oauthCallback = "null";
	
	private TencentAuthorizeDialog authDialog;
	
	private OAuthV1 oAuth = null;
	
	private void authorize(Activity ac){
		oAuth = new OAuthV1();
		authDialog = new TencentAuthorizeDialog(ac, oAuth);
		authDialog.show();
		Log.v("tag", oAuth.getOauthToken());
	}
	
	public void publishStatus(Activity ac, String msg){
		authorize(ac);
	}
}
