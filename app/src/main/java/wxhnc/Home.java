package wxhnc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.testjusttalk.Main3Activity;
import com.luwei.testjusttalk.MessageReturn;
import com.luwei.testjusttalk.R;
import com.luwei.testjusttalk.RecordSong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {

    private ListView lv;

    String userid;

    String discuss[];
    int tg=0;

    Button home,ranking,chat,me, sing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);


        lv = (ListView) findViewById(R.id.lv);
        home=(Button)findViewById(R.id.button8);
        ranking=(Button)findViewById(R.id.button9);
        sing=(Button) findViewById(R.id.button60);
        chat=(Button)findViewById(R.id.button10);
        me=(Button)findViewById(R.id.button11);


        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        System.out.print("帐号："+userid);




        //while (tg<2) { }
            //评论内容
            Map<String, String> values = new HashMap<String, String>();
            values.put("userid", userid.trim());
            http1.Request("chaxundiscuss", values);


        home.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this,Home.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        ranking.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this,Ranking.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        sing.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this,Sing.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        chat.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this,Chat.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        me.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this,Me.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });







        /*
*//*定义一个动态数组*//*
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();
*//*在数组中存放数据*//*
        for(int i=0;i<10;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.hhh);//加入图片
            map.put("ItemTitle", "第"+i+"行");
            map.put("ItemText", "这是第"+i+"行");
            listItem.add(map);
        }

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
                R.layout.item,//每一行的布局
//动态数组中的数据源的键对应到定义布局的View中
                new String[] {"ItemImage","ItemTitle", "ItemText"},
                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}
        );

        lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                setTitle("你点击了第"+arg2+"行");//设置标题栏显示点击的行

            }
        });
*/









        MyAdapter mAdapter = new MyAdapter(this);//得到一个MyAdapter对象
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




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗？");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                {

                    finish();
                }
                break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };








    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http1.Result!= null) {
                //List<String> ls = new ArrayList<String>();
                String []tem=http1.Result.toString().split("t");

                tg=tem.length;
                discuss=tem;

                /*for(int i=0;i+1<tem.length;i+=3){
                    ls.add(tem[i]+"\n"+tem[i+1]);
                }*/


            } else {
                Toast.makeText(Home.this, "加载失败！"+http1.Result, Toast.LENGTH_SHORT).show();
            }


        }
    });








    /*添加一个得到数据的方法，方便使用*/
    private ArrayList<HashMap<String, Object>> getDate(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();

        String aa[]={"孤心傲雪","愿得一人心","Frank","温柔的世界","独白","你不懂","我不在意","天使的翅膀","梦的天堂","一直等你","天使的泪","幸福密码","小傻瓜","你已不再","我要的结果","世界太吵","银河守卫","离岸等他","蓝色海洋","一起走过的昨天","LonglyHeart"};
        //String bb[]={"刚刚好-薛之谦","江南-林俊杰","倒带-周杰伦","十年-林俊杰","演员-薛之谦","刚刚好-薛之谦","演员-薛之谦","倒带-周杰伦","刚刚好-薛之谦","江南-林俊杰","演员-薛之谦","倒带-周杰伦","刚刚好-薛之谦","江南-林俊杰","演员-薛之谦","倒带-周杰伦","江南-林俊杰","刚刚好-薛之谦","倒带-周杰伦","刚刚好-薛之谦","江南-林俊杰"};
        String bb[]={"刚刚好-薛之谦","江南-林俊杰","倒带-周杰伦","十年-林俊杰","愿得一人心-李行亮","爱情转移-陈奕迅","包袱-胡彦斌","泡沫-邓紫棋","错位节拍-何艺纱","断点-张敬轩","后会无期-邓紫棋","IF YOU-韩红","老人与海-海明威","没有什么不同-曲婉婷","演员-薛之谦","倒带-周杰伦","江南-林俊杰","认真的雪-薛之谦","我们不该这样的-张赫宣","我以为-徐薇","想太多-李玖哲"};

        String cc[]={"SSS","A","S","SS","A","SS","SS","SSS","A","A","B","C","B","A","A","S","S","S","SS","A","SS"};

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日   HH:mm:ss");



        /*为动态数组添加数据*/

        int ttt=0;
        for(int i=0;i<aa.length;i++)
        {
            String diss="\n";
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.hhh);//加入图片
            map.put("ItemTitle", aa[i]+"       歌曲评价："+cc[i]+"\n\n发表了一首作品："+bb[i]+"。" );
            map.put("ItemText", formatter.format(new Date(System.currentTimeMillis())));

            try {
                if (ttt + 2 < discuss.length) {
                    while (((Integer.parseInt(discuss[ttt].toString().trim()) - 1000001) == i) && ttt + 2 < discuss.length) {

                        diss += discuss[ttt + 1] + "说：" + discuss[2] + "\n";
                        ttt += 3;
                    }
                }
            }catch (Exception e){

            }

            map.put("ItemDiscuss", diss);
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
            final ViewHolder holder;
            //观察convertView随ListView滚动情况
            Log.v("MyListViewBase", "getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.songinformation,null);
                holder = new ViewHolder();
                /*得到各个控件的对象*/
                holder.image=(ImageView) convertView.findViewById(R.id.ItemImage);
                holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
                holder.ttt = (EditText) convertView.findViewById(R.id.ItemeditText);
                holder.text = (TextView) convertView.findViewById(R.id.ItemText);
                holder.discuss = (TextView) convertView.findViewById(R.id.ItemDiscuss);
                holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
                holder.guanzhu=(Button) convertView.findViewById(R.id.button65);
                holder.btd = (Button) convertView.findViewById(R.id.Itembutton2);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
            else{
                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
            // holder.image.setImageDrawable(getDate().get(position).get("ItemImage"));
            holder.image.setBackgroundResource((Integer)getDate().get(position).get("ItemImage"));
            holder.title.setText(getDate().get(position).get("ItemTitle").toString());
            holder.discuss.setText(getDate().get(position).get("ItemDiscuss").toString());
            holder.text.setText(getDate().get(position).get("ItemText").toString());

            holder.bt.setTag(position);
            holder.btd.setTag(position);
            holder.guanzhu.setTag(position);

            // ItemListener itemListener = new ItemListener(position);//监听器记录了所在行，于是绑定到各个控件后能够返回具体的行，以及触发的控件
            /*为Button添加点击事件*/


            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(Home.this, Main3Activity.class);
                    startActivity(intent);

                }
            });



            holder.bt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //打印Button的点击信息
                    new  AlertDialog.Builder(Home.this)
                            .setTitle("系统提示")
                            .setMessage("点赞成功！")
                            .setPositiveButton("确定",
                                    new  DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public  void  onClick(DialogInterface dialog, int  which)
                                        {
                                        }
                                    }).show();

                }
            });


            holder.guanzhu.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //打印Button的点击信息
                    new  AlertDialog.Builder(Home.this)
                            .setTitle("系统提示")
                            .setMessage("关注成功！")
                            .setPositiveButton("确定",
                                    new  DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public  void  onClick(DialogInterface dialog, int  which)
                                        {
                                        }
                                    }).show();

                }
            });




            holder.btd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (holder.ttt.getText().toString().trim().length()<2){
                        new AlertDialog.Builder(Home.this)
                                .setTitle("系统提示")
                                .setMessage("请输入有效的评论内容！")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();
                    }else {

                        holder.discuss.setText(holder.discuss.getText() + "\n"+userid+"说：" + holder.ttt.getText());


                        Map<String, String> values = new HashMap<String, String>();
                        values.put("myuserid", Integer.toString(1000001+position));
                        values.put("frienduserid", userid);
                        values.put("information", holder.ttt.getText().toString().trim());
                        http.Request("fabiaopinglun", values);


                        //打印Button的点击信息
                        /*new AlertDialog.Builder(Home.this)
                                .setTitle("系统提示")
                                .setMessage("评论成功！"+position)
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();*/


                        holder.ttt.setText("");
                    }

                }
            });

            return convertView;
        }

    }
    /*存放控件*/
    public final class ViewHolder{
        public TextView title;
        public TextView discuss;
        public EditText ttt;
        public TextView text;
        public Button bt;
        public Button btd;
        public Button guanzhu;
        public ImageView image;
    }



    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    Toast.makeText(Home.this, "评论成功！" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    //Home.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(Home.this, "评论失败！"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });



}
