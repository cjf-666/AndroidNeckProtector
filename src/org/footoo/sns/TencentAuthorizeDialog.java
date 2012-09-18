package org.footoo.sns;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv1.OAuthV1;

public class TencentAuthorizeDialog extends Dialog {
	
	public final static int RESULT_CODE=1;
	
	private static final String TAG = "OAuthV1AuthorizeWebView";
	
	private OAuthV1 oAuth;
	
	private ProgressDialog progress;
	
	private LinearLayout content;
	
	protected WebView webView;
	
	private static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };

	private static final float[] DIMENSIONS_PORTRAIT = { 280, 420 };
	
	public TencentAuthorizeDialog(Activity activity, OAuthV1 auth) {
		super(activity);
		oAuth = auth;
		// TODO Auto-generated constructor stub
		
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		progress = new ProgressDialog(getContext());
		progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progress.setMessage("Loading...");

		content = new LinearLayout(getContext());
		content.setOrientation(LinearLayout.VERTICAL);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setUpWebView();

		Display display = getWindow().getWindowManager().getDefaultDisplay();
		float scale = getContext().getResources().getDisplayMetrics().density;
		float[] dimensions = display.getWidth() < display.getHeight() ? DIMENSIONS_PORTRAIT
				: DIMENSIONS_LANDSCAPE;
		addContentView(content, new FrameLayout.LayoutParams(
				(int) (dimensions[0] * scale + 0.5f), (int) (dimensions[1]
						* scale + 0.5f)));
	}



	void setUpWebView(){
		webView = new WebView(getContext());
		webView.setVerticalScrollBarEnabled(true);
		webView.setHorizontalScrollBarEnabled(false);

		FrameLayout.LayoutParams fill = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		webView.setLayoutParams(fill);
		content.addView(webView);
		
		String urlStr = OAuthConstants.OAUTH_V1_AUTHORIZE_URL+"?oauth_token="+oAuth.getOauthToken();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webView.requestFocus();
        webView.loadUrl(urlStr);
		WebViewClient client = new WebViewClient()
		{
            /**
             * 回调方法，当页面开始加载时执行
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.indexOf("checkType=verifycode") != -1) {
                    int start=url.indexOf("checkType=verifycode&v=")+23;
                    String verifyCode=url.substring(start, start+6);
                    oAuth.setOauthVerifier(verifyCode);
                    view.destroyDrawingCache();
                }
                super.onPageStarted(view, url, favicon);
            }

		};
		webView.setWebViewClient(client);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView != null) {
				webView.stopLoading();
			}
			if (progress != null) {
				progress.dismiss();
			}
			dismiss();
			this.cancel();
		}
		return super.onKeyDown(keyCode, event);
	}
}
