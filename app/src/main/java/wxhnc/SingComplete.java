package wxhnc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import wxhnc.SingComplete;
import com.luwei.testjusttalk.R;
import com.luwei.testjusttalk.RecordSong;
import com.luwei.testjusttalk.view.ILrcBuilder;
import com.luwei.testjusttalk.view.ILrcView;
import com.luwei.testjusttalk.view.ILrcViewListener;
import com.luwei.testjusttalk.view.impl.DefaultLrcBuilder;
import com.luwei.testjusttalk.view.impl.LrcRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SingComplete extends AppCompatActivity {


    String path;
    String falg=null;
    Button publicload,prevareload;
    Button listen,delete,save;

    ImageView vv;

    MediaPlayer mPlayer= new MediaPlayer();
    int fa=1;

    public final static String TAG = "SingComplete";

    //自定义LrcView，用来展示歌词
    ILrcView mLrcView;
    //更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    //更新歌词的定时器
    private Timer mTimer;
    //更新歌词的定时任务
    private TimerTask mTask; 
    
    
    
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_complete);

        Intent intent=getIntent();
        path= intent.getStringExtra("path");
        falg=intent.getStringExtra("fv").trim();


        listen=(Button)findViewById(R.id.button42);
        delete=(Button)findViewById(R.id.button43);
        save=(Button)findViewById(R.id.button44);

        final VideoView videoView = (VideoView)findViewById(R.id.videoView4);
        final AlertDialog.Builder alertDialog  =new AlertDialog.Builder(this);
        publicload=(Button)this.findViewById(R.id.button40);
        publicload.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

                alertDialog.setTitle("系统提示").setMessage("歌曲发布成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).show();

            }
        });


        prevareload=(Button)this.findViewById(R.id.button41);
        prevareload.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){


                alertDialog.setTitle("系统提示").setMessage("歌曲上传成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).show();

            }
        });


        listen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){


                if (fa==1) {


                    beginLrcPlay();
                    //mPlayer.pause();
                    //mPlayer.start();
                    videoView.start();
                    mPlayer.setVolume(0.0f,0.0f);
                    listen.setText("暂停");
                    fa=0;


                } else {

                    stopLrcPlay();
                    mPlayer.pause();
                    listen.setText("播放");
                    fa=1;
                    videoView.pause();
                    mPlayer.setVolume(0.0f,0.0f);
                }

            }
        });


        delete.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){


                alertDialog.setTitle("系统提示").setMessage("本次录制歌曲删除成功，你可以重新点歌进行录制并发布！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).show();

            }
        });


        save.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){


                alertDialog.setTitle("系统提示").setMessage("歌曲保存成功，你可以在本地进行查看！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //finish();
                    }
                }).show();

            }
        });




        //视频后期处理



        /***
         * 将播放器关联上一个音频或者视频文件
         * videoView.setVideoURI(Uri uri)
         * videoView.setVideoPath(String path)
         * 以上两个方法都可以。
         */
        videoView.setVideoPath(path);

        /**
         * w为其提供一个控制器，控制其暂停、播放……等功能
         */
        videoView.setMediaController(new MediaController(this));

        /**
         * 视频或者音频到结尾时触发的方法
         */
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.seekTo(0);
                Log.i("通知", "完成");

                stopLrcPlay();
                Intent intent1=new Intent(SingComplete.this,SingComplete.class);
                intent1.putExtra("path",path);
                intent1.putExtra("fv",falg);
                startActivity(intent1);
                finish();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("通知", "播放中出现错误");
                return false;
            }
        });





        mLrcView=(ILrcView)findViewById(R.id.lrcView57);
        vv=(ImageView) findViewById(R.id.imageView557);

        if (falg.equals("1")){
            vv.setVisibility(vv.VISIBLE);
        }else {
            vv.setVisibility(vv.INVISIBLE);
        }

        //从assets目录下读取歌词文件内容
        String lrc = getFromAssets("vvv.lrc");
        //解析歌词构造器
        ILrcBuilder builder = new DefaultLrcBuilder();
        //解析歌词返回LrcRow集合
        List<LrcRow> rows = builder.getLrcRows(lrc);
        //将得到的歌词集合传给mLrcView用来展示
        mLrcView.setLrc(rows);


        mPlayer.setVolume(0.0f,0.0f);



        //设置自定义的LrcView上下拖动歌词时监听
        /*mLrcView.setListener(new ILrcViewListener() {
            //当歌词被用户上下拖动的时候回调该方法,从高亮的那一句歌词开始播放
            public void onLrcSeeked(int newPosition, LrcRow row) {
                if (mPlayer != null) {
                    Log.d(TAG, "onLrcSeeked:" + row.time);
                    mPlayer.seekTo((int) row.time);
                }
            }
        });*/



    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
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

  //  MediaPlayer mPlayer;

    /**
     * 开始播放歌曲并同步展示歌词
     */
    public void beginLrcPlay(){
        //mPlayer = new MediaPlayer();
        try {
            //mPlayer.setDataSource(path);
            // mPlayer.setDataSource(getAssets().openFd("woggh.mp3").getFileDescriptor());
            mPlayer=MediaPlayer.create(this,R.raw.woggh);
            //准备播放歌曲监听
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //准备完毕
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    if(mTimer == null){
                        mTimer = new Timer();
                        mTask = new SingComplete.LrcTask();
                        mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                    }
                }
            });
            //歌曲播放完毕监听
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stopLrcPlay();
                }
            });
            //准备播放歌曲
            mPlayer.prepare();
            mPlayer.setVolume(0.0f,0.0f);
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
            SingComplete.this.runOnUiThread(new Runnable() {
                public void run() {
                    //滚动歌词
                    mLrcView.seekLrcToTime(timePassed);
                }
            });

        }
    };


    
    
}
