package org.footoo.cjflsq.neck.sns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent;

import org.footoo.cjflsq.neck.MyApplication;
import org.footoo.cjflsq.neck.MainActivity;
import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.userscore.ScoreManager;

public class VideoShareActivity extends Activity {

    private EditText mEditText;
    private TextView mTextLength;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_share_layout);

        Button sndBtn = (Button) findViewById(R.id.btn_send);
        sndBtn.setOnClickListener(new SendOnClickListener());

	Button retBtn = (Button) findViewById(R.id.btn_return);
	retBtn.setOnClickListener(new ReturnOnClickListener());

	int sc = ScoreManager.getInstance().getScore();
        mEditText = (EditText) findViewById(R.id.sns_share_edit);
	
	mEditText.setText("我正在学“猫头鹰”这款监测颈椎健康的手机软件里面的颈椎操，效果很好呦，你也来试试吧！");
	
	mTextLength = (TextView) findViewById(R.id.word_count);
	mTextLength.setText(new Integer(140 - mEditText.length()).toString());
        mEditText.addTextChangedListener(new TextChangedListener());
    }

    private class TextChangedListener implements TextWatcher {
        private CharSequence text;
        private int selectionStart;
        private int selectionEnd;
        private final int textLengthMax = 140;

        @Override
	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            text = s;
        }

        @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
	    public void afterTextChanged(Editable s) {
            selectionStart = mEditText.getSelectionStart();
            selectionEnd = mEditText.getSelectionEnd();
            if (text.length() > textLengthMax) {
                Toast.makeText(VideoShareActivity.this, getString(R.string.sns_update_msg_length_extends_limit).toString(), Toast.LENGTH_SHORT).show();
                s.delete(selectionStart - 1, selectionEnd);
                int temp = selectionEnd;
                mEditText.setText(s);
                mEditText.setSelection(temp);
            }
            mTextLength.setText(new Integer(140 - mEditText.length()).toString());
        }
    }

    private class SendOnClickListener implements OnClickListener {
        @Override
	    public void onClick(View v) {
            SNSSupport.shareToSina(VideoShareActivity.this, mEditText.getText().toString(), new ShareCompletedListener());
        }
    }
    
    private class ReturnOnClickListener implements OnClickListener {
        @Override
	    public void onClick(View v) {
	    VideoShareActivity.this.finish();
        }
    }

    private class ShareCompletedListener implements PublishCompleteListener {
        @Override
	    public void publishComplete(int flag) {
            VideoShareActivity.this.finish();
        }
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    VideoShareActivity.this.finish();
	}
	return super.onKeyDown(keyCode, event);
    }
}
