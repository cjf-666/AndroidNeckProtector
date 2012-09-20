package org.footoo.cjflsq.neck;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_video_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,    WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        VideoView vv = (VideoView)findViewById(R.id.videoview);
        Intent intent = getIntent();
        vv.setVideoURI(Uri.parse("android.resource://org.footoo.cjflsq.neck/raw/video" + intent.getIntExtra("id",0)));
        vv.setMediaController(new MediaController(this));
        vv.requestFocus();
        vv.start();
        vv.setOnCompletionListener(new VideoListener());
        vv.setOnErrorListener(new VideoListener());
    }

    private class VideoListener implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            VideoViewActivity.this.finish();
        }

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Toast.makeText(VideoViewActivity.this, "视频播放错误", Toast.LENGTH_LONG).show();
            VideoViewActivity.this.finish();

            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
