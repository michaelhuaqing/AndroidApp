package com.freescale.videoviewdemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewDemo extends Activity {
	private VideoView mVideoView = null;
	private String path = "/sdcard/mdtime.mp4";
	private Uri mUri;
	private int iPosWhenPaused = -1;
	private MediaController mMediaController = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		mVideoView = (VideoView) findViewById(R.id.VideoWindow);
		mUri = Uri.parse(path);
		mMediaController = new MediaController(this);
		mVideoView.setMediaController(mMediaController);
		mVideoView.requestFocus();
		
		mVideoView.setOnCompletionListener(
				new MediaPlayer.OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		
		mVideoView.setOnErrorListener(
				new MediaPlayer.OnErrorListener() {
					
					@Override
					public boolean onError(MediaPlayer mp, int what, int extra) {
						// TODO Auto-generated method stub
						return false;
					}
				});
	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		mVideoView.setVideoURI(mUri);
		mVideoView.start();
		super.onStart();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		iPosWhenPaused = mVideoView.getCurrentPosition();
		mVideoView.stopPlayback();
		super.onPause();
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(iPosWhenPaused >= 0){
			mVideoView.seekTo(iPosWhenPaused);
			iPosWhenPaused = -1;
		}
		super.onResume();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_view_demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
