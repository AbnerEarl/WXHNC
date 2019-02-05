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

public class Me extends AppCompatActivity {


    String userid;
    Button home,ranking,chat,me;
    Button sing;
    String xinxi=null;
    Button personalinfomation,myproduct,myhonour,historyandnow,safesetting,environment,themesetting,receive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_me);



        home=(Button)findViewById(R.id.button815);
        ranking=(Button)findViewById(R.id.button915);
        sing=(Button) findViewById(R.id.button64);
        chat=(Button)findViewById(R.id.button1015);
        me=(Button)findViewById(R.id.button1115);


        personalinfomation=(Button)findViewById(R.id.button19);
        myproduct=(Button)findViewById(R.id.button20);
        myhonour=(Button)findViewById(R.id.button21);
        historyandnow=(Button)findViewById(R.id.button22);
        safesetting=(Button)findViewById(R.id.button25);
        environment=(Button)findViewById(R.id.button26);
        themesetting=(Button)findViewById(R.id.button27);
        receive=(Button)findViewById(R.id.button28);


        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        System.out.print("帐号："+userid);


        home.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,Home.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        ranking.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,Ranking.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        sing.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,Sing.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        chat.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,Chat.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        me.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,Me.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });



        personalinfomation.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,PsersonalInfomation.class);
                startActivity(intent);


            }
        });

        myproduct.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,MyProduct.class);
                startActivity(intent);

            }
        });

        myhonour.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,MyHonour.class);
                startActivity(intent);


            }
        });


        home.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,Home.class);
                startActivity(intent);


            }
        });
        historyandnow.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,HistoryAndNow.class);
                startActivity(intent);


            }
        });

        safesetting.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,SafeSetting.class);
                startActivity(intent);

            }
        });


        environment.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,SettingsActivity.class);
                startActivity(intent);


            }
        });
        themesetting.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Me.this,ThemeSetting.class);
                startActivity(intent);


            }
        });

        receive.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                /*Intent intent=new Intent(Me.this,ReceiveInvite.class);
                intent.putExtra("userid",userid);
                startActivity(intent);*/


                if (xinxi!=null){
                    Intent intent = new Intent(Me.this, ReceiveInvite.class);
                    intent.putExtra("userid", userid);
                    intent.putExtra("xinxi", xinxi);
                    startActivity(intent);
                    xinxi = null;
                    receive.setText("");//收到邀请
                }
                else {
                    Intent intent = new Intent(Me.this, ReceiveInvite.class);
                    intent.putExtra("userid", " ");
                    intent.putExtra("xinxi", " ");
                    startActivity(intent);
                }


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
                http1.Request("chaxuninvite", values);

                if (xinxi!=null){

                    //@android:color/holo_blue_light
                    receive.setText("                     +1");//收到邀请
                    //xiaoxi.setBackgroundResource(R.color.colorPrimary);
                }else {
                    receive.setText("");//收到邀请
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
