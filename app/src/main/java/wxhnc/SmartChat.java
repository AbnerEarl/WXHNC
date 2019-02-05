package wxhnc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.luwei.testjusttalk.R;

import java.util.List;

public class SmartChat extends AppCompatActivity {

    Button QQ;
    Button weixin;
    Button youxiang;
    Button xinxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_chat);



        final AlertDialog.Builder alertDialog  =new AlertDialog.Builder(this);
        QQ=(Button)this.findViewById(R.id.button4);
        QQ.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                PackageManager pm=getPackageManager();
                List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);


                String appdiaoyong=null;
                for (PackageInfo packageInfo:list2) {
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    String packageName = packageInfo.packageName;

                    if (appName.equals("QQ")) {
                        appdiaoyong = packageName;
                    }
                }
                if (appdiaoyong!=null){
                    startActivity(getPackageManager().getLaunchIntentForPackage(appdiaoyong));
                }
                else {
                    alertDialog.setTitle("系统提示").setMessage("尊敬的用户，您好！您尚未开通该服务，无法体验该功能，单击“确定”前往开通！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(SmartChat.this, ApplicationLoad.class);
                            intent.putExtra("url", "");
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });


        weixin=(Button)this.findViewById(R.id.button5);
        weixin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                PackageManager pm=getPackageManager();
                List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);


                String appdiaoyong=null;
                for (PackageInfo packageInfo:list2) {
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    String packageName = packageInfo.packageName;

                    if (appName.equals("微信")) {
                        appdiaoyong = packageName;
                    }
                }
                if (appdiaoyong!=null){
                    startActivity(getPackageManager().getLaunchIntentForPackage(appdiaoyong));
                }
                else {
                    alertDialog.setTitle("系统提示").setMessage("尊敬的用户，您好！您尚未开通该服务，无法体验该功能，单击“确定”前往开通！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(SmartChat.this, ApplicationLoad.class);
                            intent.putExtra("url", "");
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });


        youxiang=(Button)this.findViewById(R.id.button6);
        youxiang.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                PackageManager pm=getPackageManager();
                List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);


                String appdiaoyong=null;
                for (PackageInfo packageInfo:list2) {
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    String packageName = packageInfo.packageName;

                    if (packageName.equals("com.android.email")||appName.equals("电子邮件")||appName.equals("邮箱")||appName.equals("电子邮箱")) {
                        appdiaoyong = packageName;
                    }
                }
                if (appdiaoyong!=null){
                    startActivity(getPackageManager().getLaunchIntentForPackage(appdiaoyong));
                }
                else {
                    alertDialog.setTitle("系统提示").setMessage("尊敬的用户，您好！您尚未开通该服务，无法体验该功能，单击“确定”前往开通！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(SmartChat.this, ApplicationLoad.class);
                            intent.putExtra("url", "");
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });



        xinxi=(Button)this.findViewById(R.id.button8);
        xinxi.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);


                String appdiaoyong = null;
                for (PackageInfo packageInfo : list2) {
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    String packageName = packageInfo.packageName;

                    if (appName.equals("信息")||appName.equals("短信")||appName.equals("短信息")) {
                        appdiaoyong = packageName;
                    }
                }
                if (appdiaoyong != null) {
                    startActivity(getPackageManager().getLaunchIntentForPackage(appdiaoyong));
                } else {
                    alertDialog.setTitle("系统提示").setMessage("尊敬的用户，您好！您尚未开通该服务，无法体验该功能，单击“确定”前往开通！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(SmartChat.this, ApplicationLoad.class);
                            intent.putExtra("url", "");
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });




    }
}
