package wxhnc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.luwei.testjusttalk.R;

public class RoomNext extends AppCompatActivity {

    Button singstart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_next);

        singstart=(Button)findViewById(R.id.button37);
        singstart.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(RoomNext.this,SingPrepare.class);
                startActivity(intent);
                finish();

            }
        });



    }
}
