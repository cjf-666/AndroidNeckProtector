package org.footoo.cjflsq.neck.sns;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.footoo.cjflsq.neck.MainActivity;
import org.footoo.cjflsq.neck.R;


public class SNSShareActivity extends Activity {

    private EditText mEditText;
    private TextView mTextLength;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_share_layout);

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null || !mNetworkInfo.isAvailable()) {
            backToMainActivity();
        }

        Button sndBtn = (Button) findViewById(R.id.btnSend);
        sndBtn.setOnClickListener(new SendOnClickListener());

        mEditText = (EditText) findViewById(R.id.sns_share_edit);
        mTextLength = (TextView) findViewById(R.id.word_count);
        mEditText.addTextChangedListener(new TextChangedListener());
    }

    private void backToMainActivity() {
        Intent intent = new Intent(SNSShareActivity.this, MainActivity.class);
        startActivity(intent);
        SNSShareActivity.this.finish();
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
                Toast.makeText(SNSShareActivity.this, getString(R.string.sns_update_msg_length_extends_limit).toString(), Toast.LENGTH_SHORT).show();
                s.delete(selectionStart - 1, selectionEnd);
                int temp = selectionEnd;
                mEditText.setText(s);
                mEditText.setSelection(temp);
            }
            mTextLength.setText(new Integer(mEditText.length()).toString() + "/140");
        }
    }

    private class SendOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            SNSSupport.shareToRenren(SNSShareActivity.this, mEditText.getText().toString(), new ShareCompletedListener());
        }
    }

    private class ShareCompletedListener implements PublishCompleteListener {
        @Override
        public void publishComplete(int flag) {
            backToMainActivity();
        }
    }
}
