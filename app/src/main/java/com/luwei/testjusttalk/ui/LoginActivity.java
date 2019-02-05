package com.luwei.testjusttalk.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.justalk.cloud.juscall.JusCallConfig;
import com.justalk.cloud.juscall.MtcCallDelegate;
import com.justalk.cloud.juscall.MtcResource;
import com.justalk.cloud.jusdoodle.DoodleDelegate;
import com.justalk.cloud.juslogin.LoginDelegate;
import com.justalk.cloud.juspush.Gcm;
import com.justalk.cloud.juspush.MiPush;
import com.justalk.cloud.lemon.MtcApi;
import com.justalk.cloud.lemon.MtcDiag;
import com.luwei.testjusttalk.R;
import com.luwei.testjusttalk.RecordSong;
import com.luwei.testjusttalk.base.baseactivity;
import com.luwei.testjusttalk.conf.JusConf;
import com.luwei.testjusttalk.utils.Helper;
import com.luwei.testjusttalk.utils.Signer;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.luwei.testjusttalk.ui.LoginActivity;

public class LoginActivity extends baseactivity implements LoginDelegate.Callback {
    private EditText mEtNumber;
    private EditText mEtPwd;
    private Button mBtLogin;
    ProgressBar denglu;
    private ListView lv;
    String userid;


    private static BroadcastReceiver mDiagTptStatisticsReceiver;
    /**
     * JusTalk Cloud 鉴权信息的配置 的路由配置
     */
    private static final String NET_WORK = "http:router.justalkcloud.com:8080";

    RoomInfomation roomInfomation=new RoomInfomation();

    /**
     * 是不是已经登录的flag
     */
    private boolean mLogined = false;

    protected void assignViews() {
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        denglu=(ProgressBar) findViewById(R.id.progressBar3);
        mBtLogin.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        System.out.print("帐号："+userid);


        assignViews();
        checkPermission();
        LoginDelegate.setCallback(this);
        copyRes();
        initBroad();



        lv=(ListView) findViewById(R.id.songinfo);





        LoginActivity.MyAdapter mAdapter = new LoginActivity.MyAdapter(this);//得到一个MyAdapter对象
        lv.setAdapter(mAdapter);//为ListView绑定Adapter
/*为ListView添加点击事件*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.v("MyListViewBase", "你点击了ListView条目" + arg2);//在LogCat中输出信息

            }
        });




    }
    
    
    
    
    
    
    
    
    
    
    

    /**
     * 复制需要的资源去sd卡
     */
    private void copyRes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    String imageName = String.format("background_%d", i);
                    String imagePath = Helper.getSendDir(context) + imageName + ".jpg";
                    int id = MtcResource.getIdByName("drawable", imageName);
                    Helper.copyImageToSdcard(context, id, imagePath);
                }
            }
        }).start();
    }

    private void initBroad() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        if (mDiagTptStatisticsReceiver == null) {
            mDiagTptStatisticsReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int sendBw = 0;
                    int recvBw = 0;
                    try {
                        String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                        JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                        sendBw = json.getInt(MtcDiag.MtcDiagSendBandwidthKey);
                        recvBw = json.getInt(MtcDiag.MtcDiagReceiveBandwidthKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    Log.e(TAG, "onReceive: " + String.format("out:%d \t in:%d", sendBw, recvBw));
//                    mEditTextBandwidth.setText(String.format("out:%d \t in:%d", sendBw, recvBw));
                }
            };
            broadcastManager.registerReceiver(mDiagTptStatisticsReceiver, new IntentFilter(MtcDiag.MtcDiagTptTestStatisticsNotification));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                mBtLogin.setVisibility(mBtLogin.INVISIBLE);
                denglu.setVisibility(denglu.VISIBLE);
                doLogin();
                break;
            default:
                break;
        }
    }


    /**
     * justalk登录操作
     */
    private void doLogin() {

        if (LoginDelegate.getInitState() == LoginDelegate.InitStat.MTC_INIT_FAIL) {
            checkPermission();
            return;
        }
        /*String number = mEtNumber.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();*/
        String number = userid.split("@")[0];
        String pwd = "0000";
        if (TextUtils.isEmpty(mEtNumber.getText().toString().trim()) || TextUtils.isEmpty(mEtNumber.getText().toString().trim())) {
            showTs("请输入完整的房间信息！");
            return;
        }
        if (LoginDelegate.login(number, pwd, NET_WORK)) {
            roomInfomation.setRoomname(mEtNumber.getText().toString().trim());
            roomInfomation.setUserid(userid);
            roomInfomation.setSongname(mEtPwd.getText().toString().trim());
            showTs("正在创建房间……");
        }
    }


    /**
     * 权限认证
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT > 22 && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LoginDelegate.init(this, JusConf.getKey());
                    MtcCallDelegate.init(this);
                    JusCallConfig.setBackIntentAction("com.justalk.cloud.sample.call.action.backfromcall");
                    MiPush.setCallPushParm();
                    DoodleDelegate.init(this);
                    String[] imgs = new String[]{"/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_0.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_1.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_2.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_3.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_4.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_5.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_6.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_7.jpg",
                            "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_8.jpg"};
                    DoodleDelegate.setBackgroundImages(imgs);
                } else {
                }
                return;
            }
        }
    }

    //    ***********************************@Override**********************************************
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LoginDelegate.enterBackground();
    }

    protected void onResume() {
        super.onResume();
        LoginDelegate.enterForeground();
    }


    // 登陆成功
    @Override
    public void mtcLoginOk() {
        SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
        if (mEtNumber.getText().toString().equals("")) {
           // mEtNumber.setText(sp.getString("username", ""));
           // mEtPwd.setText(sp.getString("password", ""));
//            mEditTextServerAddr.setText(sp.getString("server", ""));
        }
//        updateLoginState(true);
        SharedPreferences.Editor editor = sp.edit();
        /*editor.putString("username", mEtNumber.getText().toString());
        editor.putString("password", mEtPwd.getText().toString());*/
        editor.putString("username", userid.split("@")[0].toString());
        editor.putString("password", "0000");
        editor.putString("server", NET_WORK);
        editor.commit();
        MiPush.start(getApplicationContext(), JusConf.getMipushAppid(), JusConf.getMipushAppkey());
        Gcm.start(getApplicationContext(), JusConf.getGcmSenderId());
        MainActivity.toMainActivity(context);
        finish();
    }

    //登录失败
    @Override
    public void mtcLoginDidFail() {
        showTs("创建房间失败");
    }

    //登出成功
    @Override
    public void mtcLogoutOk() {
        showTs("退出成功 mtcLogoutOk");
        MiPush.stop(getApplicationContext());
        Gcm.stop(getApplicationContext());
    }

    //被登出
    @Override
    public void mtcLogouted() {
        showTs("被登出 mtcLogouted");
        MiPush.stop(getApplicationContext());
        Gcm.stop(getApplicationContext());
//        Intent i = new Intent(this, LoginActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(i);
    }

    public void mtcAuthRequire(String id, String nonce) {
        String key =
                "MIIEpAIBAAKCAQEAtzJF3SdDDZNuMit61hrIrDNROPhCnma9lp1gPaBF0tZGvn2X" + "\n" +
                        "GQWtlrQ1XEGbfTrpN1whsVB/e8SBWdmNHwE6Ze67fzYAw8B5Ul+bkO4lx79LL2If" + "\n" +
                        "n2oQ6doW9NYCCFt0CHv4A9esU1zB8SNKZazKfU/u3n/UEEInd/cJ/vMiuAWGSSPa" + "\n" +
                        "wLCqJJT7Ly+/Cgq9vK4jdX0YohBA7/ZSr7jx+9Zs2Lj4/L+y6lKR6UdXoTY0nJKf" + "\n" +
                        "jSEZCxwFCPh57snvg90fDyizn58EI1dZ977+bG5oD1zE2O4CmhLaX4tQiQCioZeP" + "\n" +
                        "D+iHTsXWYP9u8l2J/PBxVObqLBPAcqV4UyslZwIDAQABAoIBADd9X9IUEWhsTsWd" + "\n" +
                        "i/CMXlpilOinsi4eurCDbOJdyKiLRRRwIDNxF9p9LWiLatis3nVpT79Qvby0keWw" + "\n" +
                        "UuGgUpsLi/mFVwf0JguAcDOfHwx48gIhO6jizMq4x5lTtXvoj6X+PuqTClyZzRkI" + "\n" +
                        "coGHrDH240i7+XUPRLs+teVmqg6JAlVh2t3WjI7967I1wgzywchFWMSTftilULjl" + "\n" +
                        "7NKZEn0anDIJoN4Rgy0KSX9pzHHaEMmkD9bdpx/XlaXjaWpEfLB9frl8XEweixo8" + "\n" +
                        "R55Hpowk/Q4Qk73+xTvUmSO1XktyEWAumGgF1jWla5Z/5D3CadSgfJcfeePMXxCy" + "\n" +
                        "YC7TSxECgYEA6G/nOnrzNMe0dem8nt++86LOPn1wKqr0FV+AelNM2gzsoMtTUazX" + "\n" +
                        "C5Jyt6Xdq8+Y/xQ8WXmRziXlAS5753RenHbR9pY5XXP0AeMvMW4tlngGtKk0c/Rt" + "\n" +
                        "2W+eF7EI+sgI9g9TMRPjbvUGQDZl8gr2a94Q6u15tgYFJpjx8A3d1akCgYEAycR/" + "\n" +
                        "XCwVnGRqQTRA3cRhfz0usF3pyESqOoVfzzK0bSyOJAv7FOTd0V1MNhzwoWPwdTrl" + "\n" +
                        "5CGzTGxyaHZpi+sRVg5X/XolYwjgXrFKCckxmnVYlYFY30iegJfu3GR6phyTlwgc" + "\n" +
                        "nSQ2vfSseLnHTFDlrBEy/56H2MoQ4qXad5Sh7I8CgYEAsbDijyVxCbdl8QJ37OjV" + "\n" +
                        "vMGIc+NHPYclQ7WXrWxDAysANshZcMX2O+WAB38ooHD64H3iyPAUFAmKMUYM+NtQ" + "\n" +
                        "fMKlLqKXRicfsdWwvVQiS7aEQdZcwAxrcd9Pd4Mifz0vBJSgn5M5uhhc5/fuJYRV" + "\n" +
                        "8A561m4nLo0ZoPEpe7/OB8kCgYBvsplzNHCOUMTF7iCO5N24q+1B8+utU94NYbLF" + "\n" +
                        "qONboRPbfsp0KbNm6Uh8mI7aOdJvg7irD8EL6Ol5TTxnGi5RvsUVbV5vMgXMRkef" + "\n" +
                        "nUMZqCbvNVk22yPsOrAgUHvZo+5M6U+16stnY6FrgCWF6S8Mj8T04BWCfXLVlk2Y" + "\n" +
                        "b68onwKBgQDN2yjNq+B3FBI91DQGoHPWqsSbMPed/TJ6pGiSftKeckyA35tQVMAO" + "\n" +
                        "JoBCLU0G5Z2KWuqIFphjcg+YjR/Agsu/0v7ZRVyU1bDjmHW7MtBOLXPTB2QB8DF7" + "\n" +
                        "SeCrYJLUItyJaqQmxAaGQtDp8+dqm0BywAwoB2AgqDjXhph1xWMQIA==" + "\n";

        String code = Signer.signWithKey(key, id, nonce, 3600);
        LoginDelegate.promptAuthCode(code);
    }
    
    
    
    
    
    
    
    
    
    //歌曲信息


    /*添加一个得到数据的方法，方便使用*/
    private ArrayList<HashMap<String, Object>> getDate(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();

        String aa[]={"孤心傲雪","愿得一人心-李行亮","Frank","温柔的世界","独白","你不懂","我不在意","天使的翅膀","梦的天堂","一直等你","天使的泪","幸福密码","小傻瓜","你已不再","我要的结果","世界太吵","银河守卫","离岸等他","蓝色海洋","一起走过的昨天","LonglyHeart"};
        String bb[]={"刚刚好-薛之谦","江南-林俊杰","倒带-周杰伦","十年-林俊杰","愿得一人心-李行亮","爱情转移-陈奕迅","包袱-胡彦斌","泡沫-邓紫棋","错位节拍-何艺纱","断点-张敬轩","后会无期-邓紫棋","IF YOU-韩红","老人与海-海明威","没有什么不同-曲婉婷","演员-薛之谦","倒带-周杰伦","江南-林俊杰","认真的雪-薛之谦","我们不该这样的-张赫宣","我以为-徐薇","想太多-李玖哲"};
        String cc[]={"SSS","A","S","SS","A","SS","SS","SSS","A","A","B","C","B","A","A","S","S","S","SS","A","SS"};

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日   HH:mm:ss");
        /*为动态数组添加数据*/
        for(int i=0;i<bb.length;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            //map.put("ItemImage", R.drawable.hhh);//加入图片
            map.put("song", "       "+bb[i]);
            //map.put("ItemText", formatter.format(new Date(System.currentTimeMillis())));
            //map.put("ItemText", "快来说点什么吧。。。");
            listItem.add(map);
        }
        return listItem;

    }
    /*
         * 新建一个类继承BaseAdapter，实现视图与数据的绑定
         */
    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        /*构造函数*/
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

            return getDate().size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        /*书中详细解释该方法*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final LoginActivity.ViewHolder holder;
            //观察convertView随ListView滚动情况
            Log.v("MyListViewBase", "getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.singsong,null);
                holder = new LoginActivity.ViewHolder();
                    /*得到各个控件的对象*/
                holder.song = (TextView) convertView.findViewById(R.id.textView58);
                holder.ksong=(Button) convertView.findViewById(R.id.button66);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
            else{
                holder = (LoginActivity.ViewHolder)convertView.getTag();//取出ViewHolder对象
            }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/

            holder.song.setText(getDate().get(position).get("song").toString());
            holder.ksong.setTag(position);

            // ItemListener itemListener = new ItemListener(position);//监听器记录了所在行，于是绑定到各个控件后能够返回具体的行，以及触发的控件
            /*为Button添加点击事件*/





            holder.ksong.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mEtPwd.setText(holder.song.getText().toString().trim());
                }
            });


            return convertView;
        }

    }
    /*存放控件*/
    public final class ViewHolder{
        public TextView song;
        public Button ksong;

    }



    
    
}
