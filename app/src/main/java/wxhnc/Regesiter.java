package wxhnc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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


public class Regesiter extends AppCompatActivity {

    String nickname,password,yonghuid,yanzhengma;
    Button zhuce,getyanzhengma,chakanyouxiang;
    EditText nick,pass,comfirm,youxiang,yanzheng;
    int rnumber;
    String yzm=null;
    static int flag=0;
    TextView shuoming;
    ProgressBar zhucejiazai;



    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    zhucejiazai.setVisibility(zhucejiazai.INVISIBLE);
                    zhuce.setVisibility(zhuce.VISIBLE);
                    getyanzhengma.setVisibility(getyanzhengma.VISIBLE);
                    chakanyouxiang.setVisibility(chakanyouxiang.VISIBLE);
                    Toast.makeText(Regesiter.this, "注册成功" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    Regesiter.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    zhucejiazai.setVisibility(zhucejiazai.INVISIBLE);
                    zhuce.setVisibility(zhuce.VISIBLE);
                    getyanzhengma.setVisibility(getyanzhengma.VISIBLE);
                    chakanyouxiang.setVisibility(chakanyouxiang.VISIBLE);
                    Toast.makeText(Regesiter.this, "注册失败，请检查输入的邮箱是否正确以及网络环境是否异常-后重试！"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regesiter);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());



        getyanzhengma=(Button)findViewById(R.id.button2);
        chakanyouxiang=(Button)findViewById(R.id.button3);
        zhuce=(Button)findViewById(R.id.button7);

        nick=(EditText)findViewById(R.id.editText5);
        pass=(EditText)findViewById(R.id.editText6);
        comfirm=(EditText)findViewById(R.id.editText7);
        youxiang=(EditText)findViewById(R.id.editText9);
        yanzheng=(EditText)findViewById(R.id.editText10);
        shuoming=(TextView)findViewById(R.id.textView8);
        zhucejiazai=(ProgressBar)this.findViewById(R.id.progressBar2);


        final AlertDialog.Builder alertDialog  =new AlertDialog.Builder(this);
        getyanzhengma.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (youxiang.getText().toString().trim()!=null&&youxiang.getText().toString().trim().length()>4) {
                    rnumber = (int) (Math.random() * 100000);
                    //yzm=String.valueOf(rnumber) ;
                    yzm = Integer.toString(rnumber);
                    String content="您本次注册的验证码为： " + yzm + " 。十分钟内有效，请十分钟内在注册界面输入此验证码！ 如您没有进行注册操作，无需理会此邮件!";

                    try {
                        sendEmail(youxiang.getText().toString().trim(),"“我想和你唱”帐号注册邮件!", content);
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


        chakanyouxiang.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Regesiter.this, ApplicationLoad.class);
                intent.putExtra("url", "http://www.benpig.com/mail.htm");
                startActivity(intent);

            }
        });


        zhuce.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (panduan()){

                    Map<String, String> values = new HashMap<String, String>();
                    values.put("userid", youxiang.getText().toString().trim());
                    values.put("username", nick.getText().toString().trim());
                    values.put("userpass",pass.getText().toString().trim());

                    zhucejiazai.setVisibility(zhucejiazai.VISIBLE);
                    zhuce.setVisibility(zhuce.INVISIBLE);
                    getyanzhengma.setVisibility(getyanzhengma.INVISIBLE);
                    chakanyouxiang.setVisibility(chakanyouxiang.INVISIBLE);
                    http.Request("zhucexinxi",values);

                    /*alertDialog.setTitle("系统提示").setMessage("帐号注册成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Regesiter.this.finish();


                        }
                    }).show();*/

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





   /* public int sendEmail(String emailAddress, String id) {
        int tag=0;
        Properties prop = new Properties();
        Session session = null;
        Message message = null;
        Transport transport = null;
        try {
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.host", "smtp.qq.com");
            prop.setProperty("mail.smtp.port", "465");
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.ssl.enable", "true");
            session = Session.getDefaultInstance(prop);
            session.setDebug(true);
            message = new MimeMessage(session);
            message.setSubject("“我想和你唱”帐号注册邮件!");
            message.setText("您本次注册的验证码为： " + id + " 。十分钟内有效，请十分钟内在注册界面输入此验证码！ 如您没有进行注册操作，无需理会此邮件!");
            message.setFrom(new InternetAddress("1320259466@qq.com"));
            transport = session.getTransport();
            transport.connect("1320259466@qq.com", "zyeqcneqptmpbafe");
            transport.sendMessage(message, new InternetAddress[] { new InternetAddress(emailAddress) });
            tag=1;
        } catch (Exception e) {
            tag=0;
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return tag;
    }
*/



    /*public int sendMailByJavaMail(){
         Mail m=new Mail("wcfXXXX@gmail.com","XXXXX");
         m.set_debuggable(true);
         String[] toArr={"18170000@qq.com"};
         m.set_to(toArr);
        m.set_from("18170000@qq.com");
        m.set_subject("This is an email sent using icetest from an Android device");
        m.setBody("Email body. test by Java Mail");
        try{
            //m.addAttachment("/sdcard/filelocation");   
            if(m.send()){
                 Log.i("IcetestActivity","Email was sent successfully.");

                }else{
                  Log.i("IcetestActivity","Email was sent failed.");
                 }
             }catch (Exception e){
             // Toast.makeText(MailApp.this,  
             // "There was a problem sending the email.",  
            // Toast.LENGTH_LONG).show();  
                             Log.e("MailApp","Could not send email",e);
                 }

           return 1;
         }
*/



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
        if (nick.getText().toString().trim()==null||nick.getText().toString().trim().length()<2||nick.getText().toString().trim()==""){
            nick.requestFocus();
            shuoming.setText("昵称不能为空！");
            Toast.makeText(Regesiter.this,"昵称不能为空！",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (youxiang.getText().toString().trim()==null||youxiang.getText().toString().trim()==""||youxiang.getText().toString().trim().length()<2){
            youxiang.requestFocus();
            shuoming.setText("邮箱不能为空！");
            Toast.makeText(Regesiter.this,"邮箱不能为空！",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (pass.getText().toString().trim()==null||pass.getText().toString().trim()==""||pass.getText().toString().trim().length()<2){
            pass.requestFocus();
            shuoming.setText("设置密码不能为空！");
            Toast.makeText(Regesiter.this,"设置密码不能为空！",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(comfirm.getText().toString().trim()==null||comfirm.getText().toString().trim()==""||comfirm.getText().toString().trim().length()<2){
            comfirm.requestFocus();
            shuoming.setText("确认密码不能为空！");
            Toast.makeText(Regesiter.this,"确认密码不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!comfirm.getText().toString().trim().equals(pass.getText().toString().trim())){
            comfirm.setText("");
            comfirm.requestFocus();
            shuoming.setText("两次密码不一致，请重新输入！");
            Toast.makeText(Regesiter.this,"两次密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!yanzheng.getText().toString().trim().equals(yzm)||yzm==null||yanzheng.getText().toString().trim()==null||yanzheng.getText().toString().trim()==""||yanzheng.getText().toString().trim().length()<2){
            shuoming.setText("验证码不正确！");
            Toast.makeText(Regesiter.this,"验证码不正确！",Toast.LENGTH_SHORT).show();
            yanzheng.setText("");
            yanzheng.requestFocus();
            return false;

        }
        return true;
    }





}
