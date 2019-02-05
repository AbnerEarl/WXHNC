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
import com.luwei.testjusttalk.ui.AcceptInfomation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReceiveInvite extends AppCompatActivity {

    private Button accept,skip,refuse;
    TextView who,message,timee;
    String userid=null,xinxi=null,friendusername=null,frienduserid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_invite);

        accept=(Button)this.findViewById(R.id.button57);
        skip=(Button)this.findViewById(R.id.button56);
        refuse=(Button)this.findViewById(R.id.button55);
        who=(TextView) this.findViewById(R.id.textView48);
        message=(TextView) this.findViewById(R.id.textView49);
        timee=(TextView) this.findViewById(R.id.textView59);


        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        xinxi=intent.getStringExtra("xinxi");


        if (xinxi.trim()!=null&&xinxi.trim().length()>=1&&xinxi.trim()!="") {

            //while(friendusername==null) {}
            Map<String, String> values = new HashMap<String, String>();
            values.put("userid", userid.trim());
            //values.put("frienduserid",friendid.trim());
            http1.Request("invitefriendname", values);

            who.setVisibility(who.VISIBLE);

            //while (frienduserid==null) { }
            Map<String, String> kvalues = new HashMap<String, String>();
            kvalues.put("userid", userid.trim());
            //values.put("frienduserid",friendid.trim());
            http2.Request("invitefriendid", kvalues);


            message.setText("邀请你共同演唱 “ "+xinxi.trim()+" ” ！");


            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日   HH:mm:ss");
            timee.setText(formatter.format(new Date(System.currentTimeMillis())));
            who.setText(friendusername);
        }
        else {

            who.setVisibility(who.INVISIBLE);
            message.setText("没有收到邀請！");

           // accept.setVisibility(accept.INVISIBLE);
            accept.setEnabled(false);

            //refuse.setVisibility(refuse.INVISIBLE);
            refuse.setEnabled(false);
            skip.setVisibility(skip.VISIBLE);
            skip.setText("返回主页");
        }




        refuse.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ("返回主页".equals(skip.getText().toString().trim())||userid == null || xinxi == null||xinxi.trim().length()<1||xinxi.trim()=="") {
                    ReceiveInvite.this.finish();
                } else {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("myuserid", userid.trim());
                    http.Request("refuseinvite", values);

                }
            }
        });


        skip.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();


                /*if ("返回主页".equals(skip.getText().toString().trim())||userid == null || xinxi == null||xinxi.trim().length()<1||xinxi.trim()=="") {
                    ReceiveInvite.this.finish();
                } else {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("myuserid", userid.trim());
                    http.Request("hadread", values);

                }*/
            }
        });

        accept.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {




                Intent intent=new Intent(ReceiveInvite.this,AcceptInfomation.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
                 finish();

            }
        });




    }





    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    Toast.makeText(ReceiveInvite.this, "操作成功" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    ReceiveInvite.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(ReceiveInvite.this, "操作失败"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http1.Result!= null&&http1.Result.toString().trim().split(":")[0].trim().equals("true")) {
                friendusername=http1.Result.toString().trim().split(":")[1];
                who.setText(friendusername);
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
