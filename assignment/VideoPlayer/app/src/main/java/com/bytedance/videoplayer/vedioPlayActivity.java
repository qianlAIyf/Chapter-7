package com.bytedance.videoplayer;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class vedioPlayActivity extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    private Button playButton;
    private Button pauseButton;
    private VideoView videoView;

    public int videoPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.view_landscape);
            videoView = findViewById(R.id.videoView);

            videoView.setVideoPath(getVideoPath(R.raw.bytedance));

            MediaController mMediaController = new MediaController(this);
            videoView.setMediaController(mMediaController);

            videoView.start();
        }else {
            setContentView(R.layout.vedio_play);
            playButton = findViewById(R.id.playButton);
            pauseButton = findViewById(R.id.pauseButton);
            videoView = findViewById(R.id.videoView);

            videoView.setVideoPath(getVideoPath(R.raw.bytedance));

            MediaController mMediaController = new MediaController(this);
            videoView.setMediaController(mMediaController);
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);

    }
    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        videoPosition = videoView.getCurrentPosition();
        System.out.println("!!!!");
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            //横向
            setContentView(R.layout.view_landscape);
            //重新实例化组件和设置监听
            videoView = findViewById(R.id.videoView);

            videoView.setVideoPath(getVideoPath(R.raw.bytedance));

            MediaController mMediaController = new MediaController(this);
            videoView.setMediaController(mMediaController);

            videoView.seekTo(videoPosition);
            videoView.start();
        }else{
            //竖向
            setContentView(R.layout.vedio_play);
            //重新实例化组件和设置监听
            videoView = findViewById(R.id.videoView);

            videoView.setVideoPath(getVideoPath(R.raw.bytedance));

            MediaController mMediaController = new MediaController(this);
            videoView.setMediaController(mMediaController);

            videoView.seekTo(videoPosition);
            videoView.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.seekTo(videoPosition);
        if (videoPosition == 0) {
            mp.start();
        } else {
            mp.pause();
        }
    }
}
