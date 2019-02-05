package wxhnc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.testjusttalk.MessageReturn;
import com.luwei.testjusttalk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carer extends AppCompatActivity {
    String userid;
    ListView listView;
    ArrayAdapter adapter;
    Boolean flag=false;
    TextView xuanzehaoyou;
    Button startgo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carer);




        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");

        listView=(ListView)this.findViewById(R.id.lvmycarer);
        xuanzehaoyou=(TextView)this.findViewById(R.id.textView65);
        startgo=(Button)this.findViewById(R.id.button88);






        Map<String, String> values = new HashMap<String, String>();
        values.put("mynumber", userid.trim());
        //            values.put("frienduserid",friendid.trim());
        http1.Request("gaobaiduixiang", values);



        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = listView.getItemAtPosition(position);
               /* String st = "sdcard/";
                File f = new File(st+o.toString());*/
                xuanzehaoyou.setText("\n" + o.toString());
                Toast.makeText(Carer.this, "你已选择心仪歌友：\n" + o.toString(), Toast.LENGTH_SHORT).show();
                flag = true;

            }
        });



        startgo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    Toast.makeText(Carer.this, "请选择一个心仪歌友！", Toast.LENGTH_SHORT).show();
                    // return;
                } else if (flag) {
                    String friendid = xuanzehaoyou.getText().toString().split("\n")[1];
                    Intent intent = new Intent(Carer.this, MessageReturn.class);
                    intent.putExtra("userid", userid.trim());
                    intent.putExtra("frienduserid", friendid.trim());
                    startActivity(intent);
                }

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
                adapter =new ArrayAdapter(Carer.this,android.R.layout.simple_list_item_1,ls);
                listView.setAdapter(adapter);

            } else {
                Toast.makeText(Carer.this, "加载失败！" + http1.Result, Toast.LENGTH_SHORT).show();
            }
        }
    });




}
