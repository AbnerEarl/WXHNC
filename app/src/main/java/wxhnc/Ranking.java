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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luwei.testjusttalk.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Ranking extends AppCompatActivity {

    private ListView lv;
    Button home,ranking,chat,me;
    Button sing;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ranking);


        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");
        System.out.print("帐号："+userid);

        lv = (ListView) findViewById(R.id.llv);
        home=(Button)findViewById(R.id.button811);
        ranking=(Button)findViewById(R.id.button911);
        sing=(Button) findViewById(R.id.button61);
        chat=(Button)findViewById(R.id.button1011);
        me=(Button)findViewById(R.id.button1111);



        home.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Ranking.this,Home.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        ranking.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Ranking.this,Ranking.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        sing.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Ranking.this,Sing.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        chat.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Ranking.this,Chat.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();

            }
        });


        me.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Ranking.this,Me.class);
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








    /*添加一个得到数据的方法，方便使用*/
    private ArrayList<HashMap<String, Object>> getDate(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();

        int ii[]={R.drawable.tkk1,R.drawable.tkk2,R.drawable.tkk3,R.drawable.tkk4,R.drawable.tkk5,R.drawable.tkk6,R.drawable.tkk7,R.drawable.tkk8,R.drawable.tkk9,R.drawable.tkk10,R.drawable.tkk1,R.drawable.tkk2,R.drawable.tkk3,R.drawable.tkk4,R.drawable.tkk5,R.drawable.tkk6,R.drawable.tkk7,R.drawable.tkk8,R.drawable.tkk9,R.drawable.tkk10,R.drawable.tkk1,R.drawable.tkk2,R.drawable.tkk3,R.drawable.tkk4,R.drawable.tkk5,R.drawable.tkk6,R.drawable.tkk7,R.drawable.tkk8,R.drawable.tkk9,R.drawable.tkk10};
        String aa[]={"孤心傲雪","愿得一人心","Frank","温柔的世界","独白","你不懂","我不在意","天使的翅膀","梦的天堂","一直等你","天使的泪","幸福密码","小傻瓜","你已不再","我要的结果","世界太吵","银河守卫","离岸等他","蓝色海洋","一起走过的昨天","LonglyHeart"};
        String bb[]={"刚刚好-薛之谦","江南-林俊杰","倒带-周杰伦","十年-林俊杰","演员-薛之谦","刚刚好-薛之谦","演员-薛之谦","倒带-周杰伦","刚刚好-薛之谦","江南-林俊杰","演员-薛之谦","倒带-周杰伦","刚刚好-薛之谦","江南-林俊杰","演员-薛之谦","倒带-周杰伦","江南-林俊杰","刚刚好-薛之谦","倒带-周杰伦","刚刚好-薛之谦","江南-林俊杰"};
        String cc[]={"SSS","A","S","SS","A","SS","SS","SSS","A","A","B","C","B","A","A","S","S","S","SS","A","SS"};
        String dd[]={"歌圣","歌神","歌皇","歌侯","歌王","歌帅","歌将","歌士","歌迷","歌民","歌民","歌民","歌民","歌民","歌民","歌民","歌民","歌民","歌民","歌民","歌民"};

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日   HH:mm:ss");
        /*为动态数组添加数据*/
        for(int i=0;i<aa.length;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", ii[i]);//加入图片
            map.put("ItemTitle", aa[i]+"         \n\n一共发表了"+(aa.length-i)+"首作品。" );
            map.put("ItemText", "\n获得了  "+dd[i]+"  称号！");
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
            ViewHolder holder;
            //观察convertView随ListView滚动情况
            Log.v("MyListViewBase", "getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.rankinginformation, null);
                holder = new ViewHolder();
                    /*得到各个控件的对象*/
                holder.image=(ImageView) convertView.findViewById(R.id.imageView3);
                holder.title = (TextView) convertView.findViewById(R.id.textView56);
                holder.text = (TextView) convertView.findViewById(R.id.textView57);
                holder.gz = (Button) convertView.findViewById(R.id.button68);
                holder.ck = (Button) convertView.findViewById(R.id.button69);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
            else{
                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
            // holder.image.setImageDrawable(getDate().get(position).get("ItemImage"));
            holder.image.setBackgroundResource((Integer)getDate().get(position).get("ItemImage"));
            holder.title.setText(getDate().get(position).get("ItemTitle").toString());
            // holder.ttt.setText(getDate().get(position).get("editText").toString());
            holder.text.setText(getDate().get(position).get("ItemText").toString());

            holder.gz.setTag(position);
            holder.ck.setTag(position);

            // ItemListener itemListener = new ItemListener(position);//监听器记录了所在行，于是绑定到各个控件后能够返回具体的行，以及触发的控件
            /*为Button添加点击事件*/


            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



            holder.gz.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //打印Button的点击信息
                    new  AlertDialog.Builder(Ranking.this)
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





            holder.ck.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //打印Button的点击信息
                    new  AlertDialog.Builder(Ranking.this)
                            .setTitle("系统提示")
                            .setMessage("服务器拥挤，获取信息失败，请稍后再试！")
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

            return convertView;
        }

    }
    /*存放控件*/
    public final class ViewHolder{
        public TextView title;
        public TextView text;
        public Button gz;
        public Button ck;
        public ImageView image;
    }














}
