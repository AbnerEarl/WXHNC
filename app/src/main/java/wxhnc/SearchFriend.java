package wxhnc;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.testjusttalk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFriend extends AppCompatActivity {
    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    Toast.makeText(SearchFriend.this, "关注成功" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    SearchFriend.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(SearchFriend.this, "关注失败"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });





    Button chazhao,fujinren,tianjia;
    EditText sousuo;
    String userid;
    Boolean flag=false;
    ListView listView;
    TextView xuanzehaoyou;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());



        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");

        listView=(ListView)this.findViewById(R.id.lvfriends);
        xuanzehaoyou=(TextView)this.findViewById(R.id.textView64);
        chazhao=(Button)this.findViewById(R.id.button18);
        fujinren=(Button)this.findViewById(R.id.button86);
        tianjia=(Button)this.findViewById(R.id.button87);
        sousuo=(EditText)this.findViewById(R.id.editText8);
        sousuo.requestFocus();
       /* sousuo.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                sousuo.setText("");
            }
        });*/


        chazhao.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> values = new HashMap<String, String>();
                values.put("friendid", sousuo.getText().toString().trim());
                //            values.put("frienduserid",friendid.trim());
                http1.Request("chazhaofriend", values);
            }
        });

        fujinren.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Map<String, String> values = new HashMap<String, String>();
                values.put("mynumber", userid.trim());
                //            values.put("frienduserid",friendid.trim());
                http1.Request("fujinderen", values);
            }
        });


        tianjia.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!flag){
                    Toast.makeText(SearchFriend.this,"请选择一个歌友！", Toast.LENGTH_SHORT).show();
                    // return;
                }
                else if (flag){
                    String friendid=xuanzehaoyou.getText().toString().split("\n")[1];
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("myuserid", userid.trim());
                    values.put("frienduserid",friendid.trim());
                    http.Request("addfriend", values);
                }

            }
        });



        List<String> ls = new ArrayList<String>();
       /* ls.add("测试数据1\n18140650917");
        ls.add("测试数据2\n18140650898");*/


       /* Map<String,String> value=new HashMap<String,String>();
        List<Map<String,String>> ls=new ArrayList<Map<String, String>>();

        value.put("nicheng","我想和你一起");
        value.put("zhanghao", "18140650917");
        ls.add(value);

        value=new HashMap<String,String>();
        value.put("nicheng","我想和你wan");
        value.put("zhanghao", "18140650918");
        ls.add(value);*/

        adapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,ls);
        /*SimpleAdapter adapter1 =new SimpleAdapter(this,ls,android.R.layout.simple_list_item_2,
                new String[]{"nicheng","zhanghao"},
                new int[]{R.id.textView9,R.id.textView10});*/

        listView.setAdapter(adapter);
        //setListAdapter(adapter);



        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = listView.getItemAtPosition(position);
               /* String st = "sdcard/";
                File f = new File(st+o.toString());*/
                xuanzehaoyou.setText("\n"+o.toString());
                Toast.makeText(SearchFriend.this, "你已选择歌友：\n" + o.toString(), Toast.LENGTH_SHORT).show();
                flag = true;

            }
        });




    }




    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http1.Result!= null) {
                List<String> ls = new ArrayList<String>();
                String []tem=http1.Result.toString().split(":");
                for(int i=0;i+1<tem.length;i+=2){
                    ls.add(tem[i]+"\n"+tem[i+1]);
                }
                adapter =new ArrayAdapter(SearchFriend.this,android.R.layout.simple_list_item_1,ls);
                listView.setAdapter(adapter);

            } else {
                Toast.makeText(SearchFriend.this, "查找失败"+http1.Result, Toast.LENGTH_SHORT).show();
            }
        }
    });




}
