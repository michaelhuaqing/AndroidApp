package com.freescale.advancedmediaplayer;

import java.io.File;  
import java.io.FilenameFilter;  
import java.util.ArrayList;  
import java.util.List;  
import android.app.Activity;  
import android.app.AlertDialog;  
import android.content.DialogInterface;  
import android.media.MediaPlayer;  
import android.media.MediaPlayer.OnCompletionListener;  
import android.media.MediaPlayer.OnErrorListener;  
import android.os.Bundle;  
import android.os.Handler;  
import android.os.Message;  
import android.util.Log;  
import android.view.View;  
import android.widget.AdapterView;  
import android.widget.ArrayAdapter;  
import android.widget.ImageButton;  
import android.widget.ListView;  
import android.widget.SeekBar;  
import android.widget.TextView;  
  
  
public class MusicPlayer extends Activity implements Runnable {  
    /** Called when the activity is first created. */  
    private MediaPlayer MediaPlayer = null;  
    private SeekBar SeekBar = null;  
    private ListView ListView = null;  
    private ImageButton btnLast = null;  
    private ImageButton btnStart = null;  
    private ImageButton btnPause = null;  
    private ImageButton btnStop = null;  
    private ImageButton btnNext = null;  
    private ImageButton btnLoop = null;  
    private TextView TextView = null;  
    private List<String> MusicList = null;  
    private int Current = 0;  
    private int count = 0;  
    private boolean isrun = false;  
    private boolean isauto = false;  
    private static final String PATH = "/sdcard/";  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        // setContentView(R.layout.music);  
        setContentView(R.layout.activity_music_player);  
        ListView = (ListView) this.findViewById(R.id.lv_music);  
        SeekBar = (SeekBar) this.findViewById(R.id.SeekBar01);  
        TextView = (TextView) this.findViewById(R.id.mTextView);  
        TextView.setText("迷你音乐播放器");  
        btnLoop = (ImageButton) findViewById(R.id.imgbtn_loop);  
        btnLast = (ImageButton) this.findViewById(R.id.imgbtn_last);  
        btnStart = (ImageButton) this.findViewById(R.id.imgbtn_start);  
        btnPause = (ImageButton) this.findViewById(R.id.imgbtn_pause);  
        btnStop = (ImageButton) this.findViewById(R.id.imgbtn_stop);  
        btnNext = (ImageButton) this.findViewById(R.id.imgbtn_next);  
        MusicList = new ArrayList<String>();  
        MediaPlayer = new MediaPlayer();  
        // 循环按钮  
        btnLoop.setOnClickListener(new ImageButton.OnClickListener() {  
  
            public void onClick(View v) {  
                LoopMusic();  
            }  
  
        });  
  
        // 开始按钮  
        btnStart.setOnClickListener(new ImageButton.OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                PlayMusic(PATH + MusicList.get(Current));  
            }  
  
        });  
        // 下一首  
        btnNext.setOnClickListener(new ImageButton.OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                NextMusic();  
            }  
  
        });  
        // 上一首  
        btnLast.setOnClickListener(new ImageButton.OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                LastMusic();  
            }  
  
        });  
        // 暂停  
        btnPause.setOnClickListener(new ImageButton.OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                isrun = true;  
                isauto = false;  
                btnStart.setVisibility(View.VISIBLE);// 显示启动按钮  
                btnPause.setVisibility(View.GONE);// 隐藏暂停按钮  
  
                // 是否正在播放  
                if (MediaPlayer.isPlaying()) {  
                    MediaPlayer.pause();  
                }  
            }  
  
        });  
        // 停止  
        btnStop.setOnClickListener(new ImageButton.OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                StopMusic();  
            }  
  
        });  
  
        // 单击音乐播放列表，播放歌曲事件  
        ListView.setOnItemClickListener(new ListView.OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
                Current = arg2;  
                StopMusic();  
                PlayMusic(PATH + MusicList.get(Current));  
            }  
  
        });  
  
        SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {  
  
            @Override  
            public void onProgressChanged(SeekBar seekBar, int progress,  
                    boolean fromUser) {  
                if (!isauto) {  
                    // int n = mMediaPlayer.getCurrentPosition();  
                    // mMediaPlayer.pause();  
                    MediaPlayer.seekTo(progress);  
                    // mMediaPlayer.start();  
                    // isauto = true;  
                }  
  
            }  
  
            @Override  
            public void onStartTrackingTouch(SeekBar seekBar) {  
                isauto = false;  
            }  
  
            @Override  
            public void onStopTrackingTouch(SeekBar seekBar) {  
                isauto = true;  
            }  
  
        });  
        this.MusicList();  
        new Thread(this).start();  
    }  
  
    public void PlayMusic(String path) {  
  
        try {  
            btnStart.setVisibility(View.GONE);// 隐藏启动按钮  
            btnPause.setVisibility(View.VISIBLE);// 显示暂停按钮  
            if (!isrun) {  
                MediaPlayer.reset();// 重置  
                MediaPlayer.setDataSource(path);// 设置数据源  
                MediaPlayer.prepare();// 准备  
                MediaPlayer.start();// 开始播放  
                count = MediaPlayer.getDuration();  
                Log.i("TAG-count", count + "");  
                SeekBar.setMax(count);// 设置最大值.  
                this.TextView.setText("当前播放歌曲:" + MusicList.get(Current));  
                MediaPlayer  
                        .setOnCompletionListener(new OnCompletionListener() {  
  
                            @Override  
                            public void onCompletion(MediaPlayer mp) {  
                                // TODO Auto-generated method stub  
                                NextMusic();  
                            }  
  
                        });  
                MediaPlayer.setOnErrorListener(new OnErrorListener() {  
  
                    @Override  
                    public boolean onError(MediaPlayer mp, int what, int extra) {  
                        ShowDialog("Error");  
                        MediaPlayer.reset();  
                        return true;  
                    }  
  
                });  
  
            } else {  
                MediaPlayer.start();// 暂停之后接着播放  
            }  
            isauto = true;  
        } catch (Exception ex) {  
            this.ShowDialog("播放音乐异常:" + ex.getMessage());  
        }  
    }  
  
    public void LoopMusic(){  
        isrun=true;  
        if(btnLoop.isPressed()==true  && MediaPlayer.isPlaying()){  
            MediaPlayer.setLooping(true);  
            MediaPlayer.start();  
        }  
      
    }  
      
  
      
    public void NextMusic() {  
        int num = MusicList.size();  
        if (++Current >= num) {  
            Current = 0;  
        }  
        StopMusic();  
        PlayMusic(PATH + MusicList.get(Current));  
  
    }  
  
    public void LastMusic() {  
        int num = MusicList.size();  
        if (--Current < 0) {  
            Current = num - 1;  
        }  
        StopMusic();  
        PlayMusic(PATH + MusicList.get(Current));  
  
    }  
  
    @Override  
    protected void onPause() {  
        super.onPause();  
        isauto = false;  
        if (MediaPlayer.isPlaying()) {  
            MediaPlayer.stop();// 停止  
        }  
        MediaPlayer.reset();  
        MediaPlayer.release();  
        // mMediaPlayer = null;  
    }  
  
    public void StopMusic() {  
        isrun = false;  
        // isauto = false;  
        btnPause.setVisibility(View.GONE);// 隐藏暂停按钮  
        btnStart.setVisibility(View.VISIBLE);// 显示启动按钮  
  
        if (MediaPlayer.isPlaying()) {  
            MediaPlayer.stop();// 停止  
        }  
        // mSeekBar.setProgress(0);  
    }  
  
  
  
    // 关于循环播放的设置：  
    // if (Common.PLAY_MODE_SINGLE_LOOP == mPlayMode) {  
    // mMediaPlayer.setLooping(true); // 单曲循环  
    // } else {  
    // mMediaPlayer.setLooping(false); // 不循环播放  
    // }  
    // mMediaPlayer.start(); // 开始播放  
      
      
  
    /** 
     * 文件过滤器 
     *  
     * @author Louisa.Smart 
     *  
     */  
    class MusicFilter implements FilenameFilter {  
  
    @Override  
    public boolean accept(File dir, String filename) {  
        // TODO Auto-generated method stub  
        return (filename.endsWith(".mp3"));  
    }  
  
  
    }  
  
    /** 
     * 播放列表 
     */  
    public void MusicList() {  
        try {  
            File home = new File(PATH);  
            File[] f = home.listFiles(new MusicFilter());  
            if (f.length > 0) {  
                for (int i = 0; i < f.length; i++) {  
                    File file = f[i];  
                    MusicList.add(file.getName().toString());  
                }  
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  
                        android.R.layout.simple_list_item_1, MusicList);  
                ListView.setAdapter(adapter);  
            }  
        } catch (Exception ex) {  
            this.ShowDialog("显示音乐列表异常:" + ex.getMessage());  
        }  
  
    }  
  
    public void ShowDialog(String str) {  
        new AlertDialog.Builder(this).setTitle("提示").setMessage(str)  
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {  
  
                    @Override  
                    public void onClick(DialogInterface dialog, int which) {  
                    }  
  
                }).show();  
    }  
  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        while (true) {  
            try {  
                if (isauto) {  
                    int n = MediaPlayer.getCurrentPosition();  
                    Message msg = new Message();  
                    msg.what = n;  
                    handler.sendMessage(msg);  
                }  
                Thread.sleep(100);  
            } catch (Exception ex) {  
                ex.printStackTrace();  
            }  
  
        }  
    }  
  
    public Handler handler = new Handler() {  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            SeekBar.setProgress(msg.what);  
            SeekBar.invalidate();  
        }  
    };  
  
}  