package wxhnc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.testjusttalk.MessageReturn;
import com.luwei.testjusttalk.R;

import java.util.HashMap;
import java.util.Map;

public class MyMessage extends AppCompatActivity {
    TextView xiaoxi;
    Button yiyue,huiyuan,huifu;
    String userid=null,xinxi=null,friendusername=null,frienduserid=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        xinxi=intent.getStringExtra("xinxi");


        xiaoxi=(TextView)this.findViewById(R.id.textView60);
        yiyue=(Button)this.findViewById(R.id.button67);
        huifu=(Button)this.findViewById(R.id.button59) ;



        if (xinxi.trim()!=null&&xinxi.trim().length()>=1&&xinxi.trim()!="") {

            //while(friendusername==null) {}
                Map<String, String> values = new HashMap<String, String>();
                values.put("userid", userid.trim());
                //values.put("frienduserid",friendid.trim());
                http1.Request("chaxunfriendname", values);



            //while (frienduserid==null) { }
                Map<String, String> kvalues = new HashMap<String, String>();
                kvalues.put("userid", userid.trim());
                //values.put("frienduserid",friendid.trim());
                http2.Request("chaxunfriendid", kvalues);


            xiaoxi.setText(friendusername+"——对你说：\n\n         " + xinxi.trim());
        }
        else {

            huifu.setVisibility(huifu.INVISIBLE);
            huifu.setEnabled(false);

            yiyue.setText("返回主页");
        }

        yiyue.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ("返回主页".equals(yiyue.getText().toString().trim())||userid == null || xinxi == null||xinxi.trim().length()<1||xinxi.trim()=="") {
                    MyMessage.this.finish();
                } else {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("myuserid", userid.trim());
                    http.Request("hadread", values);

                }
            }
        });

        huifu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {




                Intent intent=new Intent(MyMessage.this,MessageReturn.class);
                intent.putExtra("userid", userid);
                intent.putExtra("frienduserid", frienduserid);
                //intent.putExtra("xinxi",editText.getText().toString().trim()+","+editText2.getText().toString().trim());
                startActivity(intent);

                Map<String, String> values = new HashMap<String, String>();
                values.put("myuserid", userid.trim());
                http.Request("hadread", values);

               // finish();

            }
        });


    }




    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    Toast.makeText(MyMessage.this, "已阅成功" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    MyMessage.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(MyMessage.this, "已阅失败"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http1.Result!= null&&http1.Result.toString().trim().split(":")[0].trim().equals("true")) {
                friendusername=http1.Result.toString().trim().split(":")[1];
            }
        }
    });


    WebService http2=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http2.Result!= null&&http2.Result.toString().trim().split(":")[0].trim().equals("true")) {
                frienduserid=http2.Result.toString().trim().split(":")[1];
            }
        }
    });



}
