package wxhnc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.luwei.testjusttalk.R;
import com.luwei.testjusttalk.ui.LoginActivity;

public class Sing extends AppCompatActivity {

    Button home,ranking,chat,me;
    Button sing;
    String userid;

    Button createroome,singlesing,watchsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sing);


        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        System.out.print("帐号："+userid);

        home=(Button)findViewById(R.id.button812);
        ranking=(Button)findViewById(R.id.button912);
        sing=(Button) findViewById(R.id.button62);
        chat=(Button)findViewById(R.id.button1012);
        me=(Button)findViewById(R.id.button1112);

        createroome=(Button)findViewById(R.id.button32);
        singlesing=(Button)findViewById(R.id.button33);
        watchsing=(Button)findViewById(R.id.button34);



        home.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,Home.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        ranking.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,Ranking.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        sing.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,Sing.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        chat.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,Chat.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        me.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,Me.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });



        createroome.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,LoginActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);


            }
        });

        singlesing.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,SingleSing.class);
                startActivity(intent);


            }
        });

        watchsing.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Sing.this,WatchSing.class);
                startActivity(intent);


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



}
