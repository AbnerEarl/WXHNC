package wxhnc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.luwei.testjusttalk.R;

public class SingPrepare extends AppCompatActivity {

    Button singcomplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_prepare);

        singcomplete=(Button)findViewById(R.id.button39);

        singcomplete.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SingPrepare.this,SingComplete.class);
                startActivity(intent);
                finish();

            }
        });


    }
}
