package wxhnc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.luwei.testjusttalk.R;

public class ThemeSetting extends AppCompatActivity {
Button WHITE,RED,YELLOW,BLUE,GREEN,BLACK,CYAN,DKGRAY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_setting);




        WHITE=(Button)findViewById(R.id.button70);
        RED=(Button)findViewById(R.id.button71);
        YELLOW=(Button)findViewById(R.id.button72);
        BLUE=(Button)findViewById(R.id.button73);
        GREEN=(Button)findViewById(R.id.button74);
        BLACK=(Button)findViewById(R.id.button75);


        CYAN=(Button)findViewById(R.id.button76);
        DKGRAY=(Button)findViewById(R.id.button77);
     /*   change=(Button)findViewById(R.id.button78);
        change=(Button)findViewById(R.id.button79);
        change=(Button)findViewById(R.id.button80);
        change=(Button)findViewById(R.id.button81);*/





        WHITE.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.WHITE);
            }
        });

        RED.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.RED);
            }
        });

        YELLOW.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.YELLOW);
            }
        });


        BLUE.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.BLUE);
            }
        });

        GREEN.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.GREEN);
            }
        });


        BLACK.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.BLACK);
            }
        });


        CYAN.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.CYAN);
            }
        });

        DKGRAY.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.activity_theme_setting);
                myLayout.setBackgroundColor(Color.DKGRAY);
            }
        });






    }
}
