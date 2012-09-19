package org.footoo.cjflsq.neck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
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
	/*mScrollView = (TimeSettingScrollView) findViewById(R.id.seekbar_scroll_view);
 
	  mSeekBar = getLayoutInflater().inflate(R.layout.time_seekbar_scrollview, null);
	  timeScrollButton = (Button) mSeekBar.findViewById(R.id.time_scroll_button);
	  timeScrollButton.setOnClickListener(new TimeOnClickListener());
		
	  View leftView = new View(this);
	  leftView.setBackgroundColor(Color.TRANSPARENT);
	  children = new View[]{leftView, mSeekBar};
	  mScrollView.initViews(children, new SizeCallBackForSetting(timeScrollButton), (LinearLayout) findViewById(R.id.home_page_view));
	  mScrollView.setTimeBtn(this.timeScrollButton);*/

        presentScore();
        prepareGallery();
    }

    private void prepareGallery() {
        Integer[] images = {R.drawable.guide1, R.drawable.guide1,
                R.drawable.guide1, R.drawable.guide1, R.drawable.guide1};

        ImageAdapter adapter = new ImageAdapter(this, images);
        adapter.createReflectedImages();//创建倒影效果
        GalleryFlow galleryFlow = (GalleryFlow) this.findViewById(R.id.gallery);
        galleryFlow.setFadingEdgeLength(0);
        galleryFlow.setSpacing(-50); //图片之间的间距
        galleryFlow.setAdapter(adapter);

        galleryFlow.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (parent.getSelectedItemId() == id) {
                    Toast.makeText(MyApplication.getAppContext(), "aaaaaa", Toast.LENGTH_LONG).show();
                    /*Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://org.footoo.cjflsq.neck/" + R.raw.sample.m4v);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "video/mp4");
                    startActivity(intent);*/
                }
            }

        });
        galleryFlow.setSelection(4);
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

        mImageView = (ImageView) findViewById(R.id.score_image_1);
        setImageView(mImageView, mScore % 10);
        mScore /= 10;
        mImageView = (ImageView) findViewById(R.id.score_image_0);
        setImageView(mImageView, mScore % 10);
    }

    private void setImageView(ImageView imv, int num) {
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

    private class SnsOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SNSShareActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    private class TimeOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
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
            MainActivity.this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyDown(keyCode, event);
    }

}