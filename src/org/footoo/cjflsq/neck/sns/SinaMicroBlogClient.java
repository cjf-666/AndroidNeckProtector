package org.footoo.cjflsq.neck.sns;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import com.weibo.net.*;

public class SinaMicroBlogClient {
    private static final String APP_KEY = "2685797514";
    private static final String APP_SECRET = "ae4de89d059ae4c3be4fe4f1d75ebb18";
    private String status;
    private ProgressDialog mpDialog;
    private Activity activity;
    private PublishCompleteListener mListener;

    private String token = null;
    private String expires_in = null;
    private AccessToken accessToken;

    public void publishStatus(Activity activity, String text,
                              PublishCompleteListener listener) {

        this.activity = activity;
        status = text;
        mListener = listener;

        SharedPreferences sp = activity.getApplicationContext()
                .getSharedPreferences("weibo_config", Context.MODE_PRIVATE);
        token = sp.getString("weibo_token", null);
        expires_in = sp.getString("weibo_expires_in", null);

        Weibo weibo = Weibo.getInstance();
        if (token == null || expires_in == null || token == ""
                || expires_in == "" || !weibo.isSessionValid()) {
            weibo.setupConsumerConfig(APP_KEY, APP_SECRET);
            weibo.setRedirectUrl("http://org.footoo.cjflsq/android-callback-page");
            weibo.authorize(activity, new AuthDialogListener());
        } else {
            accessToken = new AccessToken(token, APP_SECRET);
            accessToken.setExpiresIn(expires_in);
            Weibo.getInstance().setAccessToken(accessToken);
            publishStatus();
        }
    }

    private void publishStatus() {
        mpDialog = new ProgressDialog(activity);
        mpDialog.setTitle("分享到新浪微博");// 设置标题
        mpDialog.setMessage("正在发布，请稍后…");
        mpDialog.show();
        Thread t = new Thread() {
            public void run() {
                try {
                    final WeiboParameters bundle = new WeiboParameters();
                    bundle.add("source", APP_KEY);
                    bundle.add("status", status);
                    final String url = Weibo.SERVER + "statuses/update.json";
                    Weibo weibo = Weibo.getInstance();
                    weibo.request(activity, url, bundle,
                            Utility.HTTPMETHOD_POST, weibo.getAccessToken());
                    mpDialog.cancel();
                    if (mListener != null) {
                        mListener
                                .publishComplete(PublishCompleteListener.PUBLISH_SUCCESS);
                    }
                } catch (WeiboException e) {
                    // TODO Auto-generated catch block
                    Log.e("err", e.toString());
                    mpDialog.cancel();
                    if (e.getStatusCode() == 40025) {
                        if (mListener != null) {
                            mListener
                                    .publishComplete(PublishCompleteListener.REPEAT_CONTENT);
                        }
                    }
                    if (mListener != null) {
                        mListener
                                .publishComplete(PublishCompleteListener.PUBLISH_FAILED);
                    }
                }
            }
        };
        t.start();
    }

    class AuthDialogListener implements WeiboDialogListener {

        public void onComplete(Bundle values) {
            token = values.getString("access_token");
            expires_in = values.getString("expires_in");
            SharedPreferences sp = activity.getApplicationContext()
                    .getSharedPreferences("weibo_config", Context.MODE_PRIVATE);
            Editor editor = sp.edit();
            editor.putString("weibo_token", token);
            editor.putString("weibo_expires_in", expires_in);
            editor.commit();
            accessToken = new AccessToken(token, APP_SECRET);
            accessToken.setExpiresIn(expires_in);
            Weibo.getInstance().setAccessToken(accessToken);
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
