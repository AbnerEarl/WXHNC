package com.luwei.testjusttalk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.luwei.testjusttalk.view.ILrcBuilder;
import com.luwei.testjusttalk.view.ILrcView;
import com.luwei.testjusttalk.view.ILrcViewListener;
import com.luwei.testjusttalk.view.impl.DefaultLrcBuilder;
import com.luwei.testjusttalk.view.impl.LrcRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import wxhnc.SingComplete;

public class RecordSong extends AppCompatActivity  implements SurfaceHolder.Callback {



    public final static String TAG = "RecordSong";

    //自定义LrcView，用来展示歌词
    ILrcView mLrcView;
    //更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    //更新歌词的定时器
    private Timer mTimer;
    //更新歌词的定时任务
    private TimerTask mTask;


    //伴奏、原唱
   // private int ttg=R.raw.ggh;
    private String ttg=null;
    private int yyt=0;


    CamcorderProfile profile;

    private MediaPlayer mMediaPlayer;
    public String pathpa = null;
    int flag=1,falg=1;
    private SurfaceView mSurfaceView;
    ImageView fff;
    private SurfaceHolder mSurfaceHolder;
    private Button btnStart,voice,btnStop,video;
    private boolean isRecording = false;//标记是否已经在录制
    private MediaRecorder mRecorder;//音视频录制类
    private Camera mCamera = null;//相机
    private Camera.Size mSize = null;//相机的尺寸
    //private int mCameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;//默认后置摄像头
    private int mCameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;//默认前置摄像头
    private static final SparseIntArray orientations = new SparseIntArray();//手机旋转对应的调整角度

    private int[] yinyue=new int[]{R.raw.ggh,R.raw.woggh};
    static {
        orientations.append(Surface.ROTATION_0, 90);
        orientations.append(Surface.ROTATION_90, 0);
        orientations.append(Surface.ROTATION_180, 270);
        orientations.append(Surface.ROTATION_270, 180);
    }



    MediaPlayer mPlayer = new MediaPlayer();
    MediaPlayer mPlayeryc= new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindow();
        setContentView(R.layout.activity_record_song);

        /*Intent intent=getIntent();
        ttg= intent.getStringExtra("ttg");*/


        initViews();


       // playLocalFile();2

       /* if (ttg.equals("bz")){

            voice.setText("原唱模式");
            //yyt=yinyue[0];
        }else {

            voice.setText("伴奏模式");
           // yyt=yinyue[1];
        }*/






        mLrcView=(ILrcView)findViewById(R.id.lrcView52);

        //从assets目录下读取歌词文件内容
        String lrc = getFromAssets("vvv.lrc");
        //解析歌词构造器
        ILrcBuilder builder = new DefaultLrcBuilder();
        //解析歌词返回LrcRow集合
        List<LrcRow> rows = builder.getLrcRows(lrc);
        //将得到的歌词集合传给mLrcView用来展示
        mLrcView.setLrc(rows);



        //设置自定义的LrcView上下拖动歌词时监听
        mLrcView.setListener(new ILrcViewListener() {
            //当歌词被用户上下拖动的时候回调该方法,从高亮的那一句歌词开始播放
            public void onLrcSeeked(int newPosition, LrcRow row) {
                if (mPlayer != null) {
                    Log.d(TAG, "onLrcSeeked:" + row.time);
                    mPlayer.seekTo((int) row.time);
                    mPlayeryc.seekTo((int) row.time);
                }
            }
        });


    }






    @Override
                public boolean onKeyDown(int keyCode, KeyEvent event)
                {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗？");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

    }
                /**监听对话框里面的button点击事件*/
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
                {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
            {
                if (!isRecording) {

                    //startRecord();
                } else {
                    stopRecord();
                    stopLrcPlay();
                    mPlayer.stop();
                    mPlayeryc.stop();
                    mRecorder.reset();
                    releaseCamera();


                    finish();
                }
            }
                break;
            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                break;
            default:
                break;
            }
        }
    };








    //b背景音乐

    /*private void playLocalFile() {
        mMediaPlayer = MediaPlayer.create(this,R.raw.woggh);
        try {
            mMediaPlayer.prepare();
        }   catch (IllegalStateException e) {
        }    catch (IOException e) {
        }

        mMediaPlayer.start();


        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {


                stopLrcPlay();
                stopRecord();
                mRecorder.reset();
                if (yyt==1) {
                    mMediaPlayer.stop();
                }
                Intent intent=new Intent(RecordSong.this, SingComplete.class);
                intent.putExtra("path",pathpa);
                startActivity(intent);
                finish();

            }});
    }*/





    private void setWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 设置竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    private void initViews() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        btnStart = (Button) findViewById(R.id.button655);
        voice = (Button) findViewById(R.id.button665);
        btnStop = (Button) findViewById(R.id.button675);
        video = (Button) findViewById(R.id.button825);
        fff=(ImageView) findViewById(R.id.imageView55);

      /*  String path="http:/*//**************.mp3";  //这里给一个歌曲的网络地址就行了
         Uri uri =  Uri.parse(path);
         MediaPlayer player= new MediaPlayer.create(this,uri);
         player.start();*/



      /*  MediaPlayer  player= new MediaPlayer.create();
        String path="http:/*//**************.mp3"; //这里给一个歌曲的网络地址就行了
         player.setDataSource(path);
         player.prepare();
         player.start();*/



        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isRecording) {

                    startRecord();
                    //开始播放歌曲并同步展示歌词

                    beginLrcPlay();
                    beginLrcPlayyc();
                    mPlayer.setVolume(1.0f,1.0f);
                    mPlayeryc.setVolume(0.0f,0.0f);
                    btnStart.setText("重新演唱");


                } else {
                    stopRecord();
                    stopLrcPlay();
                    mRecorder.reset();

                    Intent intent=new Intent(RecordSong.this,RecordSong.class);
                   // intent.putExtra("ttg","bz");
                    startActivity(intent);
                    finish();
                }
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isRecording) {

                    //startRecord();
                } else {
                    stopRecord();
                    stopLrcPlay();
                    mPlayer.stop();
                    mPlayeryc.stop();
                    mRecorder.reset();
                    releaseCamera();
                    Intent intent=new Intent(RecordSong.this, SingComplete.class);
                    intent.putExtra("path",pathpa);
                    intent.putExtra("fv",falg+"");
                    startActivity(intent);
                    finish();
                }
            }
        });


        video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (falg==1) {

                    video.setText("录制声音");
                    falg=0;
                    fff.setVisibility(fff.INVISIBLE);

                } else {
                    video.setText("录制视频");
                    falg=1;
                    fff.setVisibility(fff.VISIBLE);
                }
            }
        });

        voice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if (flag==1){
                    voice.setText("伴奏模式");
                    mPlayer.setVolume(0.0f,0.0f);
                    mPlayeryc.setVolume(1.0f,1.0f);
                    flag=0;
                }else {
                    voice.setText("原唱模式");
                    mPlayer.setVolume(1.0f,1.0f);
                    mPlayeryc.setVolume(0.0f,0.0f);
                    flag=1;
                }




                /*if (ttg.equals("bz")){
                    if (isRecording){
                    stopRecord();
                    stopLrcPlay();
                    mRecorder.reset();

                    Intent intent=new Intent(RecordSong.this,RecordSong.class);
                    intent.putExtra("ttg","yc");
                    startActivity(intent);
                    finish();
                    }else {
                        Intent intent=new Intent(RecordSong.this,RecordSong.class);
                        intent.putExtra("ttg","yc");
                        startActivity(intent);
                        finish();
                    }
                }else {
                    if (isRecording){
                        stopRecord();
                        stopLrcPlay();
                        mRecorder.reset();

                        Intent intent=new Intent(RecordSong.this,RecordSong.class);
                        intent.putExtra("ttg","bz");
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent=new Intent(RecordSong.this,RecordSong.class);
                        intent.putExtra("ttg","bz");
                        startActivity(intent);
                        finish();
                    }
                }*/



            }
        });




        SurfaceHolder holder = mSurfaceView.getHolder();// 取得holder
        holder.setFormat(PixelFormat.TRANSPARENT);
        holder.setKeepScreenOn(true);
        holder.addCallback(this); // holder加入回调接口
    }

    /**
     * 初始化相机
     */
    private void initCamera() {
        if (Camera.getNumberOfCameras() == 2) {
            mCamera = Camera.open(mCameraFacing);
        } else {
            mCamera = Camera.open();
        }

        CameraSizeComparator sizeComparator = new CameraSizeComparator();
        Camera.Parameters parameters = mCamera.getParameters();

        if (mSize == null) {
            List<Camera.Size> vSizeList = parameters.getSupportedPreviewSizes();
            Collections.sort(vSizeList, sizeComparator);

            for (int num = 0; num < vSizeList.size(); num++) {
                Camera.Size size = vSizeList.get(num);

                if (size.width >= 800 && size.height >= 480) {
                    this.mSize = size;
                    break;
                }
            }
            mSize = vSizeList.get(0);

            List<String> focusModesList = parameters.getSupportedFocusModes();

            //增加对聚焦模式的判断
            if (focusModesList.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            } else if (focusModesList.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
            mCamera.setParameters(parameters);
        }
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int orientation = orientations.get(rotation);
        mCamera.setDisplayOrientation(orientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCamera();
    }

    @Override
    public void onPause() {
        releaseCamera();
        super.onPause();
    }

    /**
     * 开始录制
     */
    private void startRecord() {

        if (mRecorder == null) {
            mRecorder = new MediaRecorder(); // 创建MediaRecorder
        }
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.unlock();
            mRecorder.setCamera(mCamera);
        }
        try {
            // 设置音频采集方式
            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            //设置视频的采集方式
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            //设置文件的输出格式
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//aac_adif， aac_adts， output_format_rtp_avp， output_format_mpeg2ts ，webm
            //设置audio的编码格式
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //设置video的编码格式
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            //设置录制的视频编码比特率
            mRecorder.setVideoEncodingBitRate(1024 * 1024);
            //设置录制的视频帧率,注意文档的说明:
            mRecorder.setVideoFrameRate(30);
            //设置要捕获的视频的宽度和高度
            mSurfaceHolder.setFixedSize(320, 240);//最高只能设置640x480
            mRecorder.setVideoSize(320, 240);//最高只能设置640x480
            //设置记录会话的最大持续时间（毫秒）
            mRecorder.setMaxDuration(60 * 1000);
            mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
            String path = getExternalCacheDir().getPath();
            if (path != null) {
                File dir = new File(path + "/videos");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                path = dir + "/" + System.currentTimeMillis() + ".mp4";
                //设置输出文件的路径
                mRecorder.setOutputFile(path);
                pathpa=path;
                //准备录制
                mRecorder.prepare();
                //开始录制
                mRecorder.start();
                isRecording = true;
               // btnStart.setText("重新演唱");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 停止录制
     */
    private void stopRecord() {
        try {
            //停止录制
            mRecorder.stop();
            //重置
            mRecorder.reset();
           // btnStartStop.setText("开始");
        } catch (Exception e) {
            e.printStackTrace();
        }
        isRecording = false;
    }

    /**
     * 释放MediaRecorder
     */
    private void releaseMediaRecorder() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.setPreviewCallback(null);
                mCamera.unlock();
                mCamera.release();
            }
        } catch (RuntimeException e) {
        } finally {
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = holder;
        if (mCamera == null) {
            return;
        }
        try {
            //设置显示
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            releaseCamera();
            finish();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // surfaceDestroyed的时候同时对象设置为null
        if (isRecording && mCamera != null) {
            mCamera.lock();
        }
        mSurfaceView = null;
        mSurfaceHolder = null;
        releaseMediaRecorder();
        releaseCamera();
    }

    private class CameraSizeComparator implements Comparator<Camera.Size> {
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }
    }






//歌词同步模块


    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            //releaseCamera();
        }
        if (mPlayeryc != null) {
            mPlayeryc.stop();
            //releaseCamera();
        }
        releaseCamera();

    }

    /**
     * 从assets目录下读取歌词文件内容
     * @param fileName
     * @return
     */
    public String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String result="";
            while((line = bufReader.readLine()) != null){
                if(line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



    /**
     * 开始播放歌曲并同步展示歌词
     */
    public void beginLrcPlay(){

                            try {
                               // mPlayer.setDataSource(getAssets().openFd(songid).getFileDescriptor());
                                mPlayer=MediaPlayer.create(this,yinyue[0]);
                                //准备播放歌曲监听
                                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    //准备完毕
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                        if(mTimer == null){
                                            mTimer = new Timer();
                                            mTask = new RecordSong.LrcTask();
                        mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                    }


                }
            });
            //歌曲播放完毕监听
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stopLrcPlay();
                    stopRecord();
                    mRecorder.reset();

                    releaseCamera();
                    Intent intent=new Intent(RecordSong.this, SingComplete.class);
                    intent.putExtra("path",pathpa);
                    intent.putExtra("fv",falg+"");
                    startActivity(intent);
                    finish();
                }
            });
            //准备播放歌曲
            mPlayer.prepare();
            //开始播放歌曲
            mPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 原唱同步
     */
    public void beginLrcPlayyc(){

        try {
            //mPlayeryc.setDataSource(getAssets().openFd(songid).getFileDescriptor());
            mPlayeryc=MediaPlayer.create(this,yinyue[1]);
            //准备播放歌曲监听
            mPlayeryc.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //准备完毕
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    if(mTimer == null){
                        mTimer = new Timer();
                        mTask = new RecordSong.LrcTask();
                        mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                    }


                }
            });
            //歌曲播放完毕监听
            mPlayeryc.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stopLrcPlay();
                    stopRecord();
                    mRecorder.reset();

                    releaseCamera();
                    Intent intent=new Intent(RecordSong.this, SingComplete.class);
                    intent.putExtra("path",pathpa);
                    intent.putExtra("fv",falg+"");
                    startActivity(intent);
                    finish();
                }
            });
            //准备播放歌曲
            mPlayeryc.prepare();
            //开始播放歌曲
            mPlayeryc.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    /**
     * 停止展示歌曲
     */
    public void stopLrcPlay(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 展示歌曲的定时任务
     */
    class LrcTask extends TimerTask {
        @Override
        public void run() {
            //获取歌曲播放的位置
            final long timePassed = mPlayer.getCurrentPosition();
            RecordSong.this.runOnUiThread(new Runnable() {
                public void run() {
                    //滚动歌词
                    mLrcView.seekLrcToTime(timePassed);
                }
            });

        }
    };



}
