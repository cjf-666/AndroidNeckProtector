package org.footoo.cjflsq.neck.sns;

import android.app.Activity;

public class SNSSupport {
	
    public static void shareToSina(final Activity activity, final String msg, final PublishCompleteListener listener) {
    		SinaMicroBlogClient smbc = new SinaMicroBlogClient();
    		smbc.publishStatus(activity, msg, listener);
	}
    
    public static void shareToRenren(final Activity activity, final String msg, final PublishCompleteListener listener) {
		RenrenClient rc = new RenrenClient();
		rc.publishStatus(activity, msg, listener);
	}   
    public static void shareToTencent(final Activity activity, final String msg, final PublishCompleteListener listener) {
    	TencentMicroBlogClient tmbc = new TencentMicroBlogClient();
		tmbc.publishStatus(activity, msg);
	}   

}
