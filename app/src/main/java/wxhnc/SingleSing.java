package wxhnc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.luwei.testjusttalk.Main3Activity;
import com.luwei.testjusttalk.R;
import com.luwei.testjusttalk.RecordSong;
import com.luwei.testjusttalk.StartSing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SingleSing extends AppCompatActivity {

    Button kge;
    private ListView lv;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_sing);


        /*kge=(Button)findViewById(R.id.button59);
        kge.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SingleSing.this, RecordSong.class);
                startActivity(intent);
            }
        });*/
        
        lv=(ListView) findViewById(R.id.lsong);
        
        
        
        SingleSing.MyAdapter mAdapter = new SingleSing.MyAdapter(this);//得到一个MyAdapter对象
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
            final SingleSing.ViewHolder holder;
            //观察convertView随ListView滚动情况
            Log.v("MyListViewBase", "getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.singsong,null);
                holder = new SingleSing.ViewHolder();
                    /*得到各个控件的对象*/
                holder.song = (TextView) convertView.findViewById(R.id.textView58);
                holder.ksong=(Button) convertView.findViewById(R.id.button66);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
            else{
                holder = (SingleSing.ViewHolder)convertView.getTag();//取出ViewHolder对象
            }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/

            holder.song.setText(getDate().get(position).get("song").toString());
            holder.ksong.setTag(position);

            // ItemListener itemListener = new ItemListener(position);//监听器记录了所在行，于是绑定到各个控件后能够返回具体的行，以及触发的控件
            /*为Button添加点击事件*/





            holder.ksong.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                   //执行k歌事件
                    Intent intent=new Intent(SingleSing.this, RecordSong.class);
                    //intent.putExtra("ttg","bz");
                    startActivity(intent);

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
