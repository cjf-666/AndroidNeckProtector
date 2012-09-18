package org.footoo.cjflsq.neck;

import org.footoo.cjflsq.neck.gallery.GalleryFlow;
import org.footoo.cjflsq.neck.gallery.ImageAdapter;
import org.footoo.cjflsq.neck.settings.SettingsActivity;
import org.footoo.cjflsq.neck.settings.SizeCallBackForSetting;
import org.footoo.cjflsq.neck.settings.TimeSettingScrollView;
import org.footoo.cjflsq.neck.sns.SNSShareActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TimeSettingScrollView mScrollView;
	private View mSeekBar;
	private Button timeScrollButton;
	private View[] children;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Button settingButton = (Button) findViewById(R.id.settings_button);
		settingButton.setOnClickListener(new SettingOnClickListener());

		Button snsButton = (Button) findViewById(R.id.sns_share_button);
		snsButton.setOnClickListener(new SnsOnClickListener());

		mScrollView = (TimeSettingScrollView) findViewById(R.id.seekbar_scroll_view);

		mSeekBar = getLayoutInflater().inflate(
				R.layout.time_seekbar_scrollview, null);
		timeScrollButton = (Button) mSeekBar
				.findViewById(R.id.time_scroll_button);
		timeScrollButton.setOnClickListener(new TimeOnClickListener());

		View leftView = new View(this);
		leftView.setBackgroundColor(Color.TRANSPARENT);
		children = new View[] { leftView, mSeekBar };
		mScrollView.initViews(children, new SizeCallBackForSetting(
				timeScrollButton),
				(LinearLayout) findViewById(R.id.home_page_view));
		mScrollView.setTimeBtn(this.timeScrollButton);

		presentScore();
		prepareGallery();
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
		mScore = getSharedPreferences(
				getString(R.string.score_filename).toString(), 0).getInt(
				getString(R.string.everyday_score).toString(), 100);

		mProgressBar = (ProgressBar) findViewById(R.id.score_progressbar);
		mProgressBar.setProgress(mScore);

		mImageView = (ImageView) findViewById(R.id.score_image_2);
		setImageView(mImageView, mScore % 10);
		mScore /= 10;
		mImageView = (ImageView) findViewById(R.id.score_image_1);
		setImageView(mImageView, mScore % 10);
		mScore /= 10;
		mImageView = (ImageView) findViewById(R.id.score_image_0);
		setImageView(mImageView, mScore % 10);
	}

	private void setImageView(ImageView imv, int num) {
		switch (num) {
		case 0:
			imv.setImageResource(R.drawable.rating_number_0);
			break;
		case 1:
			imv.setImageResource(R.drawable.rating_number_1);
			break;
		case 2:
			imv.setImageResource(R.drawable.rating_number_2);
			break;
		case 3:
			imv.setImageResource(R.drawable.rating_number_3);
			break;
		case 4:
			imv.setImageResource(R.drawable.rating_number_4);
			break;
		case 5:
			imv.setImageResource(R.drawable.rating_number_5);
			break;
		case 6:
			imv.setImageResource(R.drawable.rating_number_6);
			break;
		case 7:
			imv.setImageResource(R.drawable.rating_number_7);
			break;
		case 8:
			imv.setImageResource(R.drawable.rating_number_8);
			break;
		case 9:
			imv.setImageResource(R.drawable.rating_number_9);
			break;
		}
	}

	private void prepareGallery() {

		Integer[] images = { R.drawable.guide1,R.drawable.guide1,R.drawable.guide1,R.drawable.guide1,R.drawable.guide1};

		ImageAdapter adapter = new ImageAdapter(this, images);
		adapter.createReflectedImages();// 创建倒影效果
		GalleryFlow galleryFlow = (GalleryFlow) this
				.findViewById(R.id.gallery);
		galleryFlow.setFadingEdgeLength(0);
		galleryFlow.setSpacing(-200); // 图片之间的间距
		galleryFlow.setAdapter(adapter);

		galleryFlow.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						String.valueOf(position), Toast.LENGTH_SHORT).show();
			}

		});
		galleryFlow.setSelection(4);
	}

	private class InfoOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,
					StatisticsActivity.class);
			startActivity(intent);
		}
	}

	private class SettingOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,
					SettingsActivity.class);
			startActivity(intent);
			MainActivity.this.finish();
		}
	}

	private class SnsOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,
					SNSShareActivity.class);
			startActivity(intent);
			MainActivity.this.finish();
		}
	}

	private class TimeOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			mScrollView.clickTimeBtn();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (TimeSettingScrollView.seekBarOut == true) {
				mScrollView.clickTimeBtn();
			} else {
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
