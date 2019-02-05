package wxhnc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.testjusttalk.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Loginn extends AppCompatActivity {

    Button shiyong,denglu;
    Button fogget,reegiester;
    File path;
    String path1;
    TextView tishi;
    EditText username,userpass;
    ProgressBar denglujiazai;






    WebService http = new WebService(new RequestFunc() {

        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) { // + http.Result
                    denglujiazai.setVisibility(denglujiazai.INVISIBLE);
                    denglu.setVisibility(denglu.VISIBLE);
                    reegiester.setVisibility(reegiester.VISIBLE);
                    fogget.setVisibility(fogget.VISIBLE);
                    shiyong.setVisibility(shiyong.VISIBLE);
                    Toast.makeText(Loginn.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Loginn.this,Home.class);
                    intent.putExtra("userid",username.getText().toString().trim());
                    startActivity(intent);
                    Loginn.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    denglu.setVisibility(denglu.VISIBLE);
                    reegiester.setVisibility(reegiester.VISIBLE);
                    fogget.setVisibility(fogget.VISIBLE);
                    shiyong.setVisibility(shiyong.VISIBLE);
                    denglujiazai.setVisibility(denglujiazai.INVISIBLE);
                    Toast.makeText(Loginn.this, "登录失败，请检查输入的邮箱是否正确以及网络环境是否异常-后重试！"+http.Result, Toast.LENGTH_SHORT).show();
                    //tishi.setText("服务器回复的信息 : " + http.Result);
                }
            }
        }
    });




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);




        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());



        shiyong=(Button)findViewById(R.id.button54);
        denglu=(Button)findViewById(R.id.button);
        fogget=(Button)findViewById(R.id.fg);
        reegiester=(Button)findViewById(R.id.button599);
        tishi=(TextView) findViewById(R.id.textView9);
        username=(EditText) findViewById(R.id.editText);
        userpass=(EditText) findViewById(R.id.editText2);
        denglujiazai=(ProgressBar) findViewById(R.id.progressBar);



        shiyong.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Loginn.this).setTitle("系统提示").setMessage("试用不能体验完整的功能，只能体验部分功能！注册后登录即可体验完整的功能！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Loginn.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Loginn.this,Home.class);
                        intent.putExtra("userid","1272275196@qq.com");
                        startActivity(intent);
                        Loginn.this.finish();
                    }
                }).setNegativeButton("取消",null).show();




            }
        });


        fogget.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Loginn.this,ForggetPassword.class);
                startActivity(intent);


            }
        });



        reegiester.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Loginn.this,Regesiter.class);
                startActivity(intent);


            }
        });


       // String path = getExternalCacheDir().getPath();
   /*     if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory();
           // path1=getApplicationContext().getFilesDir().getAbsolutePath();

            //帐号保存
//        String filename1=new String("userid.txt");
//        String filename2=new String("pass.txt");
            File f1 = new File(path + "/userid.txt");
            if (!f1.exists()) {
                try {
                    f1.createNewFile();
                } catch (Exception ex) {
                    System.out.print(ex.toString());
                }
            } else {
                try {
                    FileReader fs = new FileReader(path + "/userid.txt");
                    char[] b = new char[255];
                    fs.read(b);
                    String tem = new String(b);
                    username.setText(tem.trim());

                } catch (Exception ex) {
                    System.out.print(ex.toString());
                }
            }
            File f2 = new File(path + "/password.txt");
            if (!f2.exists()) {
                try {
                    f2.createNewFile();
                } catch (Exception ex) {
                    System.out.print(ex.toString());
                }
            } else {
                try {
                    FileReader fs = new FileReader(path + "/password.txt");
                    char[] b = new char[255];
                    fs.read(b);
                    String tem = new String(b);
                    userpass.setText(tem.trim());

                } catch (Exception ex) {
                    System.out.print(ex.toString());
                }
            }
        }*/
       /* final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/



        denglu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userin=username.getText().toString().trim();
                String userps=userpass.getText().toString().trim();
                Map<String, String> values = new HashMap<String, String>();


                if (userin==null||userin.length()<2){

                    tishi.setText("请输入用户名称！");
                    Toast.makeText(Loginn.this,"请输入用户名称！",Toast.LENGTH_SHORT).show();
                    username.requestFocus();

                }
                else if (userps==null||userps.length()<2){

                    tishi.setText("请输入用户密码！");
                    Toast.makeText(Loginn.this,"请输入用户密码！",Toast.LENGTH_SHORT).show();
                    userpass.requestFocus();
                }
                else {

                   /* try{
                        FileWriter fw=new FileWriter(path+"/userid.txt");
                        fw.write(userin);
                        fw.close();

                    }catch (Exception ex){
                        System.out.print(ex.toString());
                    }*/
                    /*try{
                        FileWriter fw=new FileWriter(path+"/password.txt");
                        fw.write(userps);
                        fw.close();

                    }catch (Exception ex){
                        System.out.print(ex.toString());
                    }*/


                    values.put("userid", userin);
                    values.put("userpass",userps);
                    denglujiazai.setVisibility(denglujiazai.VISIBLE);
                    denglu.setVisibility(denglu.INVISIBLE);
                    reegiester.setVisibility(reegiester.INVISIBLE);
                    fogget.setVisibility(fogget.INVISIBLE);
                    shiyong.setVisibility(shiyong.INVISIBLE);
                    http.Request("dengluxinxi", values);


                    //无网络环境测试
                    /*Toast.makeText(Loginn.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Loginn.this,Home.class);
                    intent.putExtra("userid",userin);
                    startActivity(intent);
                    Loginn.this.finish();*/

                }





            }
        });





    }
}
