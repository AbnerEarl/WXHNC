package com.luwei.testjusttalk;

/**
 * Created by Frank on 2017/1/16.
 */


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Player implements OnBufferingUpdateListener, OnCompletionListener, MediaPlayer.OnPreparedListener{





    public MediaPlayer mediaPlayer;
             private SeekBar skbProgress;
             private Timer mTimer=new Timer();
             public Player(SeekBar skbProgress)
             {
         this.skbProgress=skbProgress;

         try {
             mediaPlayer = new MediaPlayer();
             mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
             mediaPlayer.setOnBufferingUpdateListener(this);
             mediaPlayer.setOnPreparedListener(this);
             } catch (Exception e) {
             Log.e("mediaPlayer", "error", e);
            }

         mTimer.schedule(mTimerTask, 0, 1000);
         }

             /******************************************************* 
          * 通过定时器和Handler来更新进度条 
          ******************************************************/
             TimerTask mTimerTask = new TimerTask() {
         @Override
         public void run() {
              if(mediaPlayer==null)
             return;
             if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                 handleProgress.sendEmptyMessage(0);
                }
             }
         };

             Handler handleProgress = new Handler() {
         public void handleMessage(Message msg) {

            int position = mediaPlayer.getCurrentPosition();
             int duration = mediaPlayer.getDuration();

             if (duration > 0) {
                 long pos = skbProgress.getMax() * position / duration;
                 skbProgress.setProgress((int) pos);
                 }
            };
         };
             //*****************************************************  

             public void play()
             {
         mediaPlayer.start();
         }

             public void playUrl(String videoUrl)
             {
         try {
             mediaPlayer.reset();
             mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepare();//prepare之后自动播放  
             //mediaPlayer.start();  
             } catch (IllegalArgumentException e) {
             // TODO Auto-generated catch block  
             e.printStackTrace();
             } catch (IllegalStateException e) {
            // TODO Auto-generated catch block  
             e.printStackTrace();
             } catch (IOException e) {
             // TODO Auto-generated catch block  
             e.printStackTrace();
             }
         }

 public void pause()
             {
         mediaPlayer.pause();
         }

 public void stop()
             {
         if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            }
        }

            @Override
            /**
          * 通过onPrepared播放  
          */
             public void onPrepared(MediaPlayer arg0){
         arg0.start();
         Log.e("mediaPlayer", "onPrepared");
         }

             @Override
             public void onCompletion(MediaPlayer arg0){
         Log.e("mediaPlayer", "onCompletion");
         }

  @Override
            public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress){
         skbProgress.setSecondaryProgress(bufferingProgress);
          int currentProgress=skbProgress.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
         Log.e(currentProgress+"% play",bufferingProgress+"% buffer");
         }


}
