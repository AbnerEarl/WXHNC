package wxhnc;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.testjusttalk.Main3Activity;
import com.luwei.testjusttalk.MessageReturn;
import com.luwei.testjusttalk.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class RandFriends extends AppCompatActivity {
    String userid;
    ListView listView;
    ArrayAdapter adapter;
    Boolean flag=false;
    TextView xuanzehaoyou;
    Button trys,cation,startgo;

    int rnumber=0, sum=0;
    List<String> ls = new ArrayList<String>();
    MediaPlayer mPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rand_friends);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");


        xuanzehaoyou=(TextView)this.findViewById(R.id.textView67);
        startgo=(Button)this.findViewById(R.id.button92);
        trys=(Button)this.findViewById(R.id.button93);
        cation=(Button)this.findViewById(R.id.button94);






       // while (sum<2) {}
            Map<String, String> values = new HashMap<String, String>();
            values.put("mynumber", userid.trim());
            http1.Request("allinformation", values);


        //beginLrcPlay();
        playLocalFile();


        startgo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xuanzehaoyou.getText().toString().split("\n").length<2||xuanzehaoyou.getText().toString().trim()==null||xuanzehaoyou.getText().toString().trim().equals("")){
                    Toast.makeText(RandFriends.this,"对的时间，对的地点，不对的人。再来随机一次！", Toast.LENGTH_SHORT).show();
                    // return;
                } else {
                    String friendid = xuanzehaoyou.getText().toString().split("\n")[0];
                    Intent intent = new Intent(RandFriends.this, MessageReturn.class);
                    intent.putExtra("userid", userid.trim());
                    intent.putExtra("frienduserid", friendid.trim());
                    startActivity(intent);
                }

            }
        });



        cation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xuanzehaoyou.getText().toString().split("\n").length<2||xuanzehaoyou.getText().toString().trim()==null||xuanzehaoyou.getText().toString().trim().equals("")){
                    Toast.makeText(RandFriends.this,"对的时间，对的地点，不对的人。再来随机一次！", Toast.LENGTH_SHORT).show();
                    // return;
                }
                else {
                    String friendid=xuanzehaoyou.getText().toString().split("\n")[0];
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("myuserid", userid.trim());
                    values.put("frienduserid",friendid.trim());
                    http.Request("addfriend", values);
                }

            }
        });





        trys.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                rnumber = (int) (Math.random() * sum);
                xuanzehaoyou.setText(ls.get(rnumber) );

            }
        });




    }



    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http1.Result!= null) {
               // List<String> ls = new ArrayList<String>();
                String []tem=http1.Result.toString().split(":");
                for(int i=0;i+1<tem.length;i+=2){
                    ls.add(tem[i]+"\n"+tem[i+1]);
                }

                sum=ls.size();
                rnumber = (int) (Math.random() * sum);
                xuanzehaoyou.setText(ls.get(rnumber) );


            } else {
                Toast.makeText(RandFriends.this, "加载失败！" + http1.Result, Toast.LENGTH_SHORT).show();
            }
        }
    });

    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    Toast.makeText(RandFriends.this, "关注成功" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    RandFriends.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(RandFriends.this, "关注失败"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });






    /**
     * 开始播放歌曲
     */
    /*public void beginLrcPlay(){
        mPlayer = new MediaPlayer();
        try {
            // mPlayer.setDataSource(getAssets().openFd("woggh.mp3").getFileDescriptor());
            mPlayer=MediaPlayer.create(this,R.raw.randfriend);
            //准备播放歌曲
            mPlayer.prepare();
            mPlayer.setLooping(true);
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
*/



    private void playLocalFile() {
        mPlayer = MediaPlayer.create(this,R.raw.randfriend);
        try {
            mPlayer.prepare();
        }   catch (IllegalStateException e) {
        }    catch (IOException e) {
        }


        mPlayer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }








}
