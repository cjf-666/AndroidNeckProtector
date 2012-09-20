package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.sns.SNSSupport;
import org.footoo.cjflsq.neck.sns.SNSShareActivity;
import org.footoo.cjflsq.neck.system.TimeService;
import org.footoo.cjflsq.neck.settings.TimeSeekBarActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import org.footoo.cjflsq.neck.gallery.GalleryFlow;
import org.footoo.cjflsq.neck.gallery.ImageAdapter;
import org.footoo.cjflsq.neck.sns.SNSShareActivity;

public class MainActivity extends Activity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button snsButton = (Button) findViewById(R.id.sns_share_button);
        snsButton.setOnClickListener(new SnsOnClickListener());

        Button knButton = (Button) findViewById(R.id.knowledge_button);
        knButton.setOnClickListener(new KNOnClickListener());

        Button cinfoButton = (Button) findViewById(R.id.count_info_button);
        cinfoButton.setOnClickListener(new CountInfoOnClickListener());

	Button setButton = (Button) findViewById(R.id.time_scroll_button);
	setButton.setOnClickListener(new SetOnClickListener());

	Button expButton = (Button) findViewById(R.id.info_button);
	expButton.setOnClickListener(new ExpOnClickListener());

        presentScore();
        prepareGallery();
    }

    private void prepareGallery() {
        Integer[] images = {R.drawable.video_1, R.drawable.video_2, R.drawable.video_3};

        ImageAdapter adapter = new ImageAdapter(this, images);
        adapter.createReflectedImages();//创建倒影效果
        GalleryFlow galleryFlow = (GalleryFlow) this.findViewById(R.id.gallery);
        galleryFlow.setFadingEdgeLength(0);
        galleryFlow.setSpacing(10); //图片之间的间距
        galleryFlow.setAdapter(adapter);

        galleryFlow.setOnItemClickListener(new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		    if (parent.getSelectedItemId() == id) {
			/*  Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
			    "://org.footoo.cjflsq.neck/" + R.raw.m2);
			    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			    intent.setDataAndType(uri, "video*//*");
								 startActivity(intent);*/
			Intent intent = new Intent(MainActivity.this, VideoViewActivity.class);
			intent.putExtra("id", position + 1);
			startActivity(intent);
		    }
		}

	    });
	galleryFlow.setSelection(1);
    }

    @Override
	public void onResume() {
	super.onResume();
	presentScore();
    }

    private void presentScore() {
	int mScore;
	ProgressBar mProgressBar;
	ImageView mImageView;
	mScore = getSharedPreferences(getString(R.string.score_filename).toString(), 0).getInt(getString(R.string.everyday_score).toString(), 99);

	if (mScore >= 85) {
	    mImageView = (ImageView) findViewById(R.id.score_image_1);
	    setImageViewGreen(mImageView, mScore % 10);
	    mScore /= 10;
	    mImageView = (ImageView) findViewById(R.id.score_image_0);
	    setImageViewGreen(mImageView, mScore % 10);
	    mImageView = (ImageView) findViewById(R.id.score_image_2);
	    mImageView.setImageResource(R.drawable.green_score);
	}
	else if (mScore >= 60) {
	    mImageView = (ImageView) findViewById(R.id.score_image_1);
	    setImageViewOrange(mImageView, mScore % 10);
	    mScore /= 10;
	    mImageView = (ImageView) findViewById(R.id.score_image_0);
	    setImageViewOrange(mImageView, mScore % 10); 
	    mImageView.setImageResource(R.drawable.orange_score);
	}
	else {
	    mImageView = (ImageView) findViewById(R.id.score_image_1);
	    setImageViewRed(mImageView, mScore % 10);
	    mScore /= 10;
	    mImageView = (ImageView) findViewById(R.id.score_image_0);
	    setImageViewRed(mImageView, mScore % 10); 
	    mImageView.setImageResource(R.drawable.red_score);
	}
    }

    private void setImageViewGreen(ImageView imv, int num) {
	switch (num) {
	case 0:
	    imv.setImageResource(R.drawable.green_0);
	    break;
	case 1:
	    imv.setImageResource(R.drawable.green_1);
	    break;
	case 2:
	    imv.setImageResource(R.drawable.green_2);
	    break;
	case 3:
	    imv.setImageResource(R.drawable.green_3);
	    break;
	case 4:
	    imv.setImageResource(R.drawable.green_4);
	    break;
	case 5:
	    imv.setImageResource(R.drawable.green_5);
	    break;
	case 6:
	    imv.setImageResource(R.drawable.green_6);
	    break;
	case 7:
	    imv.setImageResource(R.drawable.green_7);
	    break;
	case 8:
	    imv.setImageResource(R.drawable.green_8);
	    break;
	case 9:
	    imv.setImageResource(R.drawable.green_9);
	    break;
	}
    }
    
    private void setImageViewOrange(ImageView imv, int num) {
	switch (num) {
	case 0:
	    imv.setImageResource(R.drawable.orange_0);
	    break;
	case 1:
	    imv.setImageResource(R.drawable.orange_1);
	    break;
	case 2:
	    imv.setImageResource(R.drawable.orange_2);
	    break;
	case 3:
	    imv.setImageResource(R.drawable.orange_3);
	    break;
	case 4:
	    imv.setImageResource(R.drawable.orange_4);
	    break;
	case 5:
	    imv.setImageResource(R.drawable.orange_5);
	    break;
	case 6:
	    imv.setImageResource(R.drawable.orange_6);
	    break;
	case 7:
	    imv.setImageResource(R.drawable.orange_7);
	    break;
	case 8:
	    imv.setImageResource(R.drawable.orange_8);
	    break;
	case 9:
	    imv.setImageResource(R.drawable.orange_9);
	    break;
	}
    }

    private void setImageViewRed (ImageView imv, int num) {
	switch (num) {
	case 0:
	    imv.setImageResource(R.drawable.red_0);
	    break;
	case 1:
	    imv.setImageResource(R.drawable.red_1);
	    break;
	case 2:
	    imv.setImageResource(R.drawable.red_2);
	    break;
	case 3:
	    imv.setImageResource(R.drawable.red_3);
	    break;
	case 4:
	    imv.setImageResource(R.drawable.red_4);
	    break;
	case 5:
	    imv.setImageResource(R.drawable.red_5);
	    break;
	case 6:
	    imv.setImageResource(R.drawable.red_6);
	    break;
	case 7:
	    imv.setImageResource(R.drawable.red_7);
	    break;
	case 8:
	    imv.setImageResource(R.drawable.red_8);
	    break;
	case 9:
	    imv.setImageResource(R.drawable.red_9);
	    break;
	}
    }

    private class SnsOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View v) {
	    ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
	    NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
	    if (mNetworkInfo == null || !mNetworkInfo.isAvailable()) {
		Toast.makeText(MyApplication.getAppContext(), getString(R.string.sns_update_network_unavailable), Toast.LENGTH_LONG).show();
	    }
	    Intent intent = new Intent(MainActivity.this, SNSShareActivity.class);
	    startActivity(intent);
	}
    }

    private class CountInfoOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View view) {
	    Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
	    startActivity(intent);
	}
    }

    private class KNOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View v) {
	    Intent intent = new Intent(MainActivity.this, KNActivity.class);
	    startActivity(intent);
	}
    }

    private class SetOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View v) {
	    Intent intent = new Intent(MainActivity.this, TimeSeekBarActivity.class);
	    startActivity(intent);
	}
    }

    private class ExpOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View v) {
	    Intent intent = new Intent(MainActivity.this, ExplainActivity.class);
	    startActivity(intent);
	}
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    MainActivity.this.finish();
	}
	return super.onKeyDown(keyCode, event);
    }

}