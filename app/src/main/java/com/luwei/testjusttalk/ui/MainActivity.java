package com.luwei.testjusttalk.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.justalk.cloud.juscall.JusCallConfig;
import com.justalk.cloud.juscall.MtcCallDelegate;
import com.justalk.cloud.lemon.MtcApi;
import com.justalk.cloud.lemon.MtcCall;
import com.justalk.cloud.lemon.MtcCallConstants;
import com.justalk.cloud.lemon.MtcCallDb;
import com.justalk.cloud.lemon.MtcCli;
import com.justalk.cloud.lemon.MtcCliConstants;
import com.justalk.cloud.lemon.MtcConstants;
import com.justalk.cloud.lemon.MtcMediaConstants;
import com.luwei.testjusttalk.MessageReturn;
import com.luwei.testjusttalk.R;
import com.luwei.testjusttalk.RecordSong;
import com.luwei.testjusttalk.base.baseactivity;
import com.luwei.testjusttalk.utils.Helper;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wxhnc.Carer;
import wxhnc.RequestFunc;
import wxhnc.WebService;

/**
 * Created by Administrator on 2016/12/1.
 */
public class MainActivity extends baseactivity {

    private EditText mEtTalkNumber;
    private Button mBtTalkAudio;
    private Button mBtTalkVideo;
    private RadioGroup mRgTalkBity;
    private RadioButton mRbTalkBityNano;
    private RadioButton mRbTalkBityMin;
    private RadioButton mRbTalkBityLow;
    private RadioButton mRbTalkBityMid;
    private RadioButton mRbTalkBity720p;
    private RadioGroup mRgTalkMack;
    private RadioButton mRbTalkMackLow;
    private RadioButton mRbTalkMackMid;
    private RadioButton mRbTalkMackHigh;
    private RadioGroup mRgTalkAuto;
    private RadioButton mRbTalkAutoOff;
    private RadioButton mRbTalkAutoAudio;
    private RadioButton mRbTalkAutoVideo;
    private Button mBtTalkDoodle;
    private Button mBtTalkRecord;
    private boolean fg=false;

    private List<String> list = new ArrayList<String>();

    List<String> ls = new ArrayList<String>();

    RoomInfomation roomInfomation=new RoomInfomation();

    //private TextView myTextView;
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter;

    //    通话媒体已接通事件
    private static BroadcastReceiver sMtcCallTalkingReceiver;
    //    通话结束事件，对方挂断
    private static BroadcastReceiver sMtcCallTermedReceiver;
    //    通话结束事件，主动挂断
    private static BroadcastReceiver sMtcCallDidTermReceiver;
    //    通话呼入事件
    private static BroadcastReceiver sMtcInComeReceiver;
    //    通话呼出事件
    private static BroadcastReceiver sMtcOutGoingReceiver;
    //    被叫振铃事件
    private static BroadcastReceiver sMtcAlertReceiver;
    //    通话已建立事件
    private static BroadcastReceiver sMtcConnectingReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        if (MtcCli.Mtc_CliGetState() != MtcCliConstants.EN_MTC_CLI_STATE_LOGINED) {
            finish();
            return;
        }
        checkPermission();
        initBroadCastReceiver();

        Map<String, String> values = new HashMap<String, String>();
        values.put("mynumber",roomInfomation.getUserid() );
        //            values.put("frienduserid",friendid.trim());
        http1.Request("gaobaiduixiang", values);

        mBtTalkAudio.setEnabled(false);
        mBtTalkVideo.setEnabled(false);


        mBtTalkDoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtTalkNumber.getText().toString().trim()==null||mEtTalkNumber.getText().toString().trim().length()<4){
                    Toast.makeText(MainActivity.this,"输入好友帐号或选择一个好友，进行邀请K歌！",Toast.LENGTH_SHORT).show();
                }else {


                        Map<String, String> values = new HashMap<String, String>();
                        values.put("myuserid", roomInfomation.getUserid());
                        values.put("frienduserid",mEtTalkNumber.getText().toString().trim());
                        values.put("songname", roomInfomation.getSongname());
                        values.put("roomname",roomInfomation.getRoomname());
                        http.Request("invitefriend", values);

                }
            }
        });

          //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项

                        /*list.add("LonglyHerart:1320259466");
                        list.add("不想和你做朋友:1272275196");
                        list.add("另一个宇宙:1926254917");
                        list.add("小琳君_joker:857350169");
                        list.add("繁华落尽:20659865");*/
                       // myTextView  =  (TextView)findViewById(R.id.TextView_city);
                        mySpinner  =  (Spinner)findViewById(R.id.spinner);
                        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。        
                        adapter  =  new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,  ls);
                        //第三步：为适配器设置下拉列表下拉时的菜单样式。        
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
                        //第四步：将适配器添加到下拉列表上        
                        mySpinner.setAdapter(adapter);
                        //mySpinner.setSelection(0,false);
                        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中        
                        mySpinner.setOnItemSelectedListener(new  Spinner.OnItemSelectedListener(){        
                                    public  void  onItemSelected(AdapterView<?> arg0, View  arg1, int  arg2, long  arg3)  {
                                                //  TODO  Auto-generated  method  stub        
                                                /*  将所选mySpinner  的值带入myTextView  中*/        
                                                //myTextView.setText("您选择的是："+  adapter.getItem(arg2));
                                        if (fg==false){
                                            fg=true;
                                        }else {
                                            mEtTalkNumber.setText(adapter.getItem(arg2).toString().trim().split(":")[0]);
                                        }




                                                /*  将mySpinner  显示*/        
                                                arg0.setVisibility(View.VISIBLE);
                                        }        
                                    public  void  onNothingSelected(AdapterView<?>  arg0)  {        
                                                //  TODO  Auto-generated  method  stub        
                                                //myTextView.setText("NONE");
                                        //mEtTalkNumber.setText("请输入对方帐号或选择一个好友！");
                                                arg0.setVisibility(View.VISIBLE);
                                        }        
                            });        
                        /*下拉菜单弹出的内容选项触屏事件处理*/        
                        mySpinner.setOnTouchListener(new  Spinner.OnTouchListener(){        
                                    public  boolean  onTouch(View  v,  MotionEvent event)  {
                                                //  TODO  Auto-generated  method  stub        
                                                /**  
                                                   *    
                                                   */    
                                                return  false;        
                                        }    
                            });        
                        /*下拉菜单弹出的内容选项焦点改变事件处理*/        
                        mySpinner.setOnFocusChangeListener(new  Spinner.OnFocusChangeListener(){        
                            public  void  onFocusChange(View  v,  boolean  hasFocus)  {        
                                        //  TODO  Auto-generated  method  stub        
                    
                                }        
                            });        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    private void initBroadCastReceiver() {

//        通话媒体已接通事件
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        if (sMtcCallTalkingReceiver == null) {
            sMtcCallTalkingReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    showLogAndTs("房间配置已完成！");
                    int dwCallId = MtcConstants.INVALIDID;
                    try {
                        String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                        JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                        dwCallId = json.getInt(MtcCallConstants.MtcCallIdKey);
                        Intent intent1=new Intent(MainActivity.this, RecordSong.class);
                        startActivity(intent1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    showLogAndTs(Helper.getSendDir(context) + System.currentTimeMillis() + ".avi");
                    //调用开始录制接口Mtc_CallRecRecvVideoStart
                    int result_code = MtcCall.Mtc_CallRecRecvVideoStart(dwCallId, Helper.getSendDir(context) + System.currentTimeMillis() + ".avi", MtcMediaConstants.EN_MTC_MFILE_AVI_H264,
                            300, "xxxx");
                    Log.e(TAG, "onReceive for start: " + result_code);
                    if (result_code == MtcConstants.ZOK) {
                        showLogAndTs("ok");
                    } else if (result_code == MtcConstants.ZFAILED) {
                        showLogAndTs("ZFAILED");
                    } else {
                        showLogAndTs("INVALIDID");
                    }
                }
            };
            broadcastManager.registerReceiver(sMtcCallTalkingReceiver,
                    new IntentFilter(MtcCallConstants.MtcCallTalkingNotification));
        }


//            通话结束事件，对方挂断
        if (sMtcCallTermedReceiver == null) {
            sMtcCallTermedReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int dwCallId = MtcConstants.INVALIDID;
                    showLogAndTs("对方退出房间！");
                    try {
                        String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                        JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                        dwCallId = json.getInt(MtcCallConstants.MtcCallIdKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    //这里要先判断下 callId 是否和通话开始的那个callId 一致，如果一致才调用停止录制接口
                    int result_code = MtcCall.Mtc_CallRecRecvVideoStop(dwCallId);
                    Log.e(TAG, "onReceive for Mtc_CallRecRecvVideoStop: " + result_code);
                }
            };
            broadcastManager.registerReceiver(sMtcCallTermedReceiver,
                    new IntentFilter(MtcCallConstants.MtcCallTermedNotification));
        }


//             通话结束事件，主动挂断
        if (sMtcCallDidTermReceiver == null) {
            sMtcCallDidTermReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int dwCallId = MtcConstants.INVALIDID;
                    showLogAndTs("对方退出房间！");
                    try {
                        String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                        JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                        dwCallId = json.getInt(MtcCallConstants.MtcCallIdKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    //这里要先判断下 callId 是否和通话开始的那个callId 一致，如果一致才调用停止录制接口
                    int result_code = MtcCall.Mtc_CallRecRecvVideoStop(dwCallId);
                    Log.e(TAG, "onReceive for Mtc_CallRecRecvVideoStop: " + result_code);
                }
            };
            broadcastManager.registerReceiver(sMtcCallDidTermReceiver,
                    new IntentFilter(MtcCallConstants.MtcCallDidTermNotification));
        }
//        通话呼入事件
        if (sMtcInComeReceiver == null) {
            sMtcInComeReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    showLogAndTs("成功进入房间！");
                }
            };
            broadcastManager.registerReceiver(sMtcInComeReceiver,
                    new IntentFilter(MtcCallConstants.MtcCallIncomingNotification));
        }
//        通话呼出
        if (sMtcOutGoingReceiver == null) {
            sMtcOutGoingReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    showLogAndTs("成功进入房间！");
                }
            };
            broadcastManager.registerReceiver(sMtcOutGoingReceiver, new IntentFilter(MtcCallConstants.MtcCallOutgoingNotification));
        }

//        被叫振铃
        if (sMtcAlertReceiver == null) {
            sMtcAlertReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //showLogAndTs("被叫振铃");
                }
            };
            broadcastManager.registerReceiver(sMtcAlertReceiver, new IntentFilter(MtcCallConstants.MtcCallAlertedNotification));
        }
        //    通话已建立事件
        if (sMtcConnectingReceiver == null) {
            sMtcConnectingReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //showLogAndTs("对话已建立事件");
                }
            };
            broadcastManager.registerReceiver(sMtcConnectingReceiver, new IntentFilter(MtcCallConstants.MtcCallConnectingNotification));
        }
    }


    public static void toMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void assignViews() {
        mEtTalkNumber = (EditText) findViewById(R.id.et_talk_number);
        mBtTalkAudio = (Button) findViewById(R.id.bt_talk_audio);
        mBtTalkAudio.setOnClickListener(this);
        mBtTalkVideo = (Button) findViewById(R.id.bt_talk_video);
        mBtTalkVideo.setOnClickListener(this);
        mRgTalkBity = (RadioGroup) findViewById(R.id.rg_talk_bity);
        mRbTalkBityNano = (RadioButton) findViewById(R.id.rb_talk_bity_nano);
        mRbTalkBityMin = (RadioButton) findViewById(R.id.rb_talk_bity_min);
        mRbTalkBityLow = (RadioButton) findViewById(R.id.rb_talk_bity_low);
        mRbTalkBityMid = (RadioButton) findViewById(R.id.rb_talk_bity_mid);
        mRbTalkBity720p = (RadioButton) findViewById(R.id.rb_talk_bity_720p);
        mRgTalkMack = (RadioGroup) findViewById(R.id.rg_talk_mack);
        mRbTalkMackLow = (RadioButton) findViewById(R.id.rb_talk_mack_low);
        mRbTalkMackMid = (RadioButton) findViewById(R.id.rb_talk_mack_mid);
        mRbTalkMackHigh = (RadioButton) findViewById(R.id.rb_talk_mack_high);
        mRgTalkAuto = (RadioGroup) findViewById(R.id.rg_talk_auto);
        mRbTalkAutoOff = (RadioButton) findViewById(R.id.rb_talk_auto_off);
        mRbTalkAutoAudio = (RadioButton) findViewById(R.id.rb_talk_auto_audio);
        mRbTalkAutoVideo = (RadioButton) findViewById(R.id.rb_talk_auto_video);
        mBtTalkDoodle = (Button) findViewById(R.id.bt_talk_doodle);
        //mBtTalkDoodle.setOnClickListener(this);
        mBtTalkRecord = (Button) findViewById(R.id.bt_talk_record);
        mBtTalkRecord.setOnClickListener(this);


        int mode = JusCallConfig.getBitrateMode();
        switch (mode) {
            case JusCallConfig.BITRATE_MODE_NANO:
                mRgTalkBity.check(R.id.rb_talk_bity_nano);
                break;
            case JusCallConfig.BITRATE_MODE_MIN:
                mRgTalkBity.check(R.id.rb_talk_bity_min);
                break;
            case JusCallConfig.BITRATE_MODE_LOW:
                mRgTalkBity.check(R.id.rb_talk_bity_low);
                break;
            case JusCallConfig.BITRATE_MODE_NORMAL:
                mRgTalkBity.check(R.id.rb_talk_bity_mid);
                break;
            case JusCallConfig.BITRATE_MODE_720P:
                mRgTalkBity.check(R.id.rb_talk_bity_720p);
                break;
            default:
                break;
        }
        // 比特率
        mRgTalkBity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int value = 0;
                switch (checkedId) {
                    case R.id.rb_talk_bity_nano:
                        value = JusCallConfig.BITRATE_MODE_NANO;
                        break;
                    case R.id.rb_talk_bity_min:
                        value = JusCallConfig.BITRATE_MODE_MIN;
                        break;
                    case R.id.rb_talk_bity_low:
                        value = JusCallConfig.BITRATE_MODE_LOW;
                        break;
                    case R.id.rb_talk_bity_mid:
                        value = JusCallConfig.BITRATE_MODE_NORMAL;
                        break;
                    case R.id.rb_talk_bity_720p:
                        value = JusCallConfig.BITRATE_MODE_720P;
                        break;
                    default:
                        break;
                }
                JusCallConfig.setBitrateMode(value);
            }
        });
        // 比特率
        mRgTalkMack.check(R.id.rb_talk_mack_low);
        mRgTalkMack.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int value = 300;
                switch (checkedId) {
                    case R.id.rb_talk_mack_low:
                        value = 300;
                        break;
                    case R.id.rb_talk_mack_mid:
                        value = 500;
                        break;
                    case R.id.rb_talk_mack_high:
                        value = 800;
                        break;
                }
                MtcCallDb.Mtc_CallDbSetVideoNackRttRange(100, value);
            }
        });
        // mack

        /*boolean autoAnswer = JusCallConfig.getIsAutoAnswerEnable();
        boolean autoAnswerWithVideo = JusCallConfig.getIsAutoAnswerWithVideo();*/

        boolean autoAnswer = true;
        boolean autoAnswerWithVideo = true;

        if (autoAnswer) {
            if (autoAnswerWithVideo) {
                mRgTalkAuto.check(R.id.rb_talk_auto_video);
            } else {
                mRgTalkAuto.check(R.id.rb_talk_auto_audio);
            }
        } else {
            mRgTalkAuto.check(R.id.rb_talk_auto_off);
        }

        JusCallConfig.setAutoAnswer(true, true);
        mRgTalkAuto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                // TODO Auto-generated method stub
                boolean enable = false;
                boolean video = false;
                switch (id) {
                    case R.id.rb_talk_auto_off:
                        enable = false;
                        video = false;
                        break;
                    case R.id.rb_talk_auto_audio:
                        enable = true;
                        video = false;
                        break;
                    case R.id.rb_talk_auto_video:
                        enable = true;
                        video = true;
                        break;
                }
                JusCallConfig.setAutoAnswer(enable, video);
            }
        });
        // auto


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_talk_audio:
                audioTalk();
                break;
            case R.id.bt_talk_video:
                videoTalk();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private void audioTalk() {
        String number = mEtTalkNumber.getText().toString().split("@")[0];
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "请输入对方帐号！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 是否需要旋转功能
        JusCallConfig.setCapacityEnabled(JusCallConfig.MAGNIFIER_ENABLED_KEY, false);
        MtcCallDelegate.call(number, null, null, false, null);
    }

    /**
     *
     */
    private void videoTalk() {
        String number = mEtTalkNumber.getText().toString().split("@")[0];
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "请输入对方帐号！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 是否需要旋转功能
        JusCallConfig.setCapacityEnabled(JusCallConfig.MAGNIFIER_ENABLED_KEY, false);
        MtcCallDelegate.call(number, null, null, true, null);
    }


    /**
     * 权限确认
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) return;
        boolean noRecord = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED;
        boolean noCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        if (noRecord && noCamera) {
            String[] permissions = new String[]{android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, 0);
        } else if (noRecord) {
            String[] permissions = new String[]{android.Manifest.permission.RECORD_AUDIO};
            ActivityCompat.requestPermissions(this, permissions, 0);
        } else if (noCamera) {
            String[] permissions = new String[]{Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
    }


    private void showLogAndTs(String... msg) {
        for (String str : msg) {
            Log.e(TAG, "showLogAndTs: " + str);
            showTs(str.toString());
        }

    }




    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {

            if (http1.Result!= null) {

                String []tem=http1.Result.toString().split(":");
                for(int i=0;i+1<tem.length;i+=2){
                    ls.add(tem[i]+":"+tem[i+1]);
                }


            } else {
                Toast.makeText(MainActivity.this, "歌友信息获取失败！" + http1.Result, Toast.LENGTH_SHORT).show();
            }
        }
    });



    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {

                    mBtTalkAudio.setEnabled(true);
                    mBtTalkVideo.setEnabled(true);
                    Toast.makeText(MainActivity.this, "发送成功" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    //MainActivity.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(MainActivity.this, "发送失败! 请检查网络是否异常，歌友帐号是否正确，稍后重试！\n"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });


}
