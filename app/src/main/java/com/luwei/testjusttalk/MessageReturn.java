package com.luwei.testjusttalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import wxhnc.RequestFunc;
import wxhnc.WebService;

public class MessageReturn extends AppCompatActivity {
    String userid,frienduserid=null;
    Button fasong;
    EditText neirong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_return);

        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        frienduserid=intent.getStringExtra("frienduserid");

        fasong=(Button)this.findViewById(R.id.button82) ;
        neirong=(EditText)this.findViewById(R.id.editText26) ;


        /*Map<String, String> values = new HashMap<String, String>();
        values.put("userid", userid.trim());
        //values.put("frienduserid",friendid.trim());
        http1.Request("chaxunfriendid", values);*/



        fasong.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (frienduserid==null){
                    /*Map<String, String> values = new HashMap<String, String>();
                    values.put("userid", userid.trim());
                    http1.Request("chaxunfriendid", values);*/
                }else {
                    if (neirong.getText().toString().trim().length() < 2) {
                        Toast.makeText(MessageReturn.this, "发送内容不能为空！", Toast.LENGTH_SHORT).show();
                    } else {

                        Map<String, String> values = new HashMap<String, String>();
                        values.put("myuserid", userid.trim());
                        values.put("frienduserid", frienduserid.trim());
                        values.put("information", neirong.getText().toString().trim());
                        http.Request("addinformation", values);
                    }
                }
            }
        });





    }




    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    Toast.makeText(MessageReturn.this, "发送成功" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    MessageReturn.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(MessageReturn.this, "发送失败"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });



    /*WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http1.Result!= null&&http1.Result.toString().trim().split(":")[0].trim().equals("true")) {
                frienduserid=http1.Result.toString().trim().split(":")[1];
            }
        }
    });*/





}
