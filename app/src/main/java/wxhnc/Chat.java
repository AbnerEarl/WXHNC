package wxhnc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.luwei.testjusttalk.R;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {

    Button home,ranking,chat,me;
    Button sing;
    Button searchfriend,fllower,cation,randfriends,matching,mymessage,otherchat;
    String xinxi=null;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);


        home=(Button)findViewById(R.id.button813);
        ranking=(Button)findViewById(R.id.button913);
        sing=(Button) findViewById(R.id.button63);
        chat=(Button)findViewById(R.id.button1013);
        me=(Button)findViewById(R.id.button1113);

        searchfriend=(Button)findViewById(R.id.button14);
        fllower=(Button)findViewById(R.id.button13);
        cation=(Button)findViewById(R.id.button12);
        randfriends=(Button)findViewById(R.id.button15);
        matching=(Button)findViewById(R.id.button16);
        mymessage=(Button)findViewById(R.id.button17);
        otherchat=(Button)findViewById(R.id.button29);



        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        System.out.print("帐号："+userid);

        home.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Home.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        ranking.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Ranking.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        sing.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Sing.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        chat.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Chat.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        me.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Me.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });




        searchfriend.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,SearchFriend.class);
                intent.putExtra("userid",userid);
                startActivity(intent);

            }
        });



        fllower.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Fllower.class);
                intent.putExtra("userid",userid);
                startActivity(intent);

            }
        });


        cation.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Carer.class);
                intent.putExtra("userid",userid);
                startActivity(intent);

            }
        });


        randfriends.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,RandFriends.class);
                intent.putExtra("userid",userid);
                startActivity(intent);

            }
        });


        matching.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,Matching.class);
                startActivity(intent);

            }
        });


        mymessage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (xinxi!=null){
                    Intent intent = new Intent(Chat.this, MyMessage.class);
                    intent.putExtra("userid", userid);
                    intent.putExtra("xinxi", xinxi);
                    startActivity(intent);
                    xinxi = null;
                    mymessage.setText("");//我的消息
                }
                else {
                    Intent intent = new Intent(Chat.this, MyMessage.class);
                    intent.putExtra("userid", " ");
                    intent.putExtra("xinxi", " ");
                    startActivity(intent);
                }

//                Intent intent=new Intent(Chat.this,MyMessage.class);
//                startActivity(intent);

            }
        });


        otherchat.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat.this,SmartChat.class);
                startActivity(intent);

            }
        });





        //获取实时相关消息
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run () {

                //信息
                Map<String, String> values = new HashMap<String, String>();
                values.put("userid", userid.trim());
                //values.put("frienduserid",friendid.trim());
                http1.Request("chaxunxiaoxi", values);

                if (xinxi!=null){

                    //@android:color/holo_blue_light
                    mymessage.setText("                  +1");//我的消息
                    //xiaoxi.setBackgroundResource(R.color.colorPrimary);
                }else {
                    mymessage.setText("");//我的消息
                }




                handler.postDelayed(this,2000);
            }
        };
        handler.postDelayed(runnable, 3000);




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

                    finish();
                }
                break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };







    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http1.Result!= null&&http1.Result.toString().trim().split(":")[0].trim().equals("true")) {
                xinxi=http1.Result.toString().trim().split(":")[1];
            } else {
                xinxi=null;
                //Toast.makeText(Main22Activity.this, "查找失败" + http1.Result, Toast.LENGTH_SHORT).show();
            }
        }
    });






}
