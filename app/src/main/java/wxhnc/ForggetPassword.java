package wxhnc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.testjusttalk.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class ForggetPassword extends AppCompatActivity {


    Button huoquyan,chakanyan,tijiao;
    EditText youxiang,yanzheng;
    static int flag=0,flagz=0;
    int rnumber;
    String yzm=null;
    TextView shuoming;
    ProgressBar tijiaojiazai;





    WebService http = new WebService(new RequestFunc() {

        @Override
        public void Func() {
            final AlertDialog.Builder alertDialog  =new AlertDialog.Builder(ForggetPassword.this);
            if (http.Result != null) {
                if (http.Result.toString().trim().length()>2) { // + http.Result
                    tijiaojiazai.setVisibility(tijiaojiazai.INVISIBLE);
                    tijiao.setVisibility(tijiao.VISIBLE);
                    chakanyan.setVisibility(chakanyan.VISIBLE);
                    huoquyan.setVisibility(huoquyan.VISIBLE);


                    if (youxiang.getText().toString().trim()!=null&&youxiang.getText().toString().trim().length()>4) {

                        String content="您的帐号找回密码为： " + http.Result.toString().trim() + " 。我想和你唱，竭诚为您服务！ 如您没有进行此操作，无需理会此邮件!";

                        try {
                            sendEmail(youxiang.getText().toString().trim(),"“我想和你唱”密码找回邮件!", content);
                            flagz=1;
                        } catch (Exception e) {
                            flagz=0;
                            e.printStackTrace();
                        }
                        if (flagz==1) {


                            alertDialog.setTitle("系统提示").setMessage("密码找回成功，马上登录邮箱查看！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ForggetPassword.this, ApplicationLoad.class);
                                    intent.putExtra("url", "http://www.benpig.com/mail.htm");
                                    startActivity(intent);
                                    ForggetPassword.this.finish();


                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ForggetPassword.this.finish();
                                }
                            }).show();

                            flagz=0;

                        } else {
                            alertDialog.setTitle("系统提示").setMessage("密码找回失败，请检查输入的邮箱是否正确以及网络环境是否异常-后重试！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            }).show();
                        }
                    }else {
                        alertDialog.setTitle("系统提示").setMessage("请输入完整的且合法的邮箱！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).show();
                    }






                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    tijiao.setVisibility(tijiao.VISIBLE);
                    huoquyan.setVisibility(huoquyan.VISIBLE);
                    chakanyan.setVisibility(chakanyan.VISIBLE);

                    tijiaojiazai.setVisibility(tijiaojiazai.INVISIBLE);
                    Toast.makeText(ForggetPassword.this, "密码找回失败，请检查输入的邮箱是否正确以及网络环境是否异常-后重试！"+http.Result, Toast.LENGTH_SHORT).show();
                    //tishi.setText("服务器回复的信息 : " + http.Result);
                }
            }
        }
    });




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgget_password);

        huoquyan=(Button)findViewById(R.id.hq);
        chakanyan=(Button)findViewById(R.id.ckyzm);
        tijiao=(Button)findViewById(R.id.tj);
        shuoming=(TextView) findViewById(R.id.xq);
        youxiang=(EditText)findViewById(R.id.yx);
        yanzheng=(EditText)findViewById(R.id.yzz);
        tijiaojiazai=(ProgressBar)this.findViewById(R.id.pgb);


        final AlertDialog.Builder alertDialog  =new AlertDialog.Builder(ForggetPassword.this);

        huoquyan.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (youxiang.getText().toString().trim()!=null&&youxiang.getText().toString().trim().length()>4) {
                    rnumber = (int) (Math.random() * 100000);
                    //yzm=String.valueOf(rnumber) ;
                    yzm = Integer.toString(rnumber);
                    String content="您本次密码找回操作的验证码为： " + yzm + " 。十分钟内有效，请十分钟内在验证界面输入此验证码！ 如您没有进行此操作，无需理会此邮件!";

                    try {
                        sendEmail(youxiang.getText().toString().trim(),"“我想和你唱”帐号验证邮件!", content);
                        flag=1;
                    } catch (Exception e) {
                        flag=0;
                        e.printStackTrace();
                    }
                    if (flag==1) {
                        alertDialog.setTitle("系统提示").setMessage("验证码获取成功，请登录邮箱查看！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                flag=0;

                            }
                        }).show();
                    } else {
                        alertDialog.setTitle("系统提示").setMessage("验证码获取失败，请检查网络并重新获取！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).show();
                    }
                }else {
                    alertDialog.setTitle("系统提示").setMessage("请输入完整的且合法的邮箱！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    }).show();
                }


            }
        });


        chakanyan.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForggetPassword.this, ApplicationLoad.class);
                intent.putExtra("url", "http://www.benpig.com/mail.htm");
                startActivity(intent);

            }
        });


        tijiao.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (panduan()){

                    Map<String, String> values = new HashMap<String, String>();
                    values.put("userid", youxiang.getText().toString().trim());

                    tijiaojiazai.setVisibility(tijiaojiazai.VISIBLE);
                    tijiao.setVisibility(tijiao.INVISIBLE);
                    huoquyan.setVisibility(huoquyan.INVISIBLE);
                    chakanyan.setVisibility(chakanyan.INVISIBLE);
                    http.Request("zhaohuimima",values);



                }
                /*else {
                    alertDialog.setTitle("系统提示").setMessage("验证码不正确，请登录邮箱查看，或者重新获取验证码！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    }).show();
                }*/


            }
        });




    }













    /**
     * 邮件发送程序
     *
     * @param to
     *            接受人
     * @param subject
     *            邮件主题
     * @param content
     *            邮件内容
     * @throws Exception
     * @throws MessagingException
     */
    public static void sendEmail(String to, String subject, String content) throws Exception, MessagingException {
        String host = "smtp.qq.com";
        String address = "1320259466@qq.com";
        String from = "1320259466@qq.com";
        String password = "zyeqcneqptmpbafe";// 密码
        if ("".equals(to) || to == null) {
            to = "1272275196@qq.com";
        }
        String port = "25";
        SendEmaill(host, address, from, password, to, port, subject, content);
    }

    /**
     * 邮件发送程序
     *
     * @param host
     *            邮件服务器 如：smtp.qq.com
     * @param address
     *            发送邮件的地址 如：545099227@qq.com
     * @param from
     *            来自： wsx2miao@qq.com
     * @param password
     *            您的邮箱密码
     * @param to
     *            接收人
     * @param port
     *            端口（QQ:25）
     * @param subject
     *            邮件主题
     * @param content
     *            邮件内容
     * @throws Exception
     */
    public static void SendEmaill(String host, String address, String from, String password, String to, String port, String subject, String content) throws Exception {
        Multipart multiPart;
        String finalString = "";

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", address);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        Log.i("Check", "done pops");
        Session session = Session.getDefaultInstance(props, null);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(finalString.getBytes(), "text/plain"));
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setDataHandler(handler);
        Log.i("Check", "done sessions");

        multiPart = new MimeMultipart();
        InternetAddress toAddress;
        toAddress = new InternetAddress(to);
        message.addRecipient(Message.RecipientType.TO, toAddress);
        Log.i("Check", "added recipient");
        message.setSubject(subject);
        message.setContent(multiPart);
        message.setText(content);

        Log.i("check", "transport");
        Transport transport = session.getTransport("smtp");
        Log.i("check", "connecting");
        transport.connect(host, address, password);
        Log.i("check", "wana send");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        Log.i("check", "sent");
        flag=1;
    }



    public Boolean panduan(){
        if (youxiang.getText().toString().trim()==null||youxiang.getText().toString().trim()==""||youxiang.getText().toString().trim().length()<2){
            youxiang.requestFocus();
            shuoming.setText("邮箱不能为空！");

            return false;
        }
        else if (!yanzheng.getText().toString().trim().equals(yzm)||yzm==null||yanzheng.getText().toString().trim()==null||yanzheng.getText().toString().trim()==""||yanzheng.getText().toString().trim().length()<2){
            shuoming.setText("验证码不正确！");
            yanzheng.setText("");
            yanzheng.requestFocus();
            return false;

        }
        return true;
    }





}
