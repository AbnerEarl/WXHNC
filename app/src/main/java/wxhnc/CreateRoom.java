package wxhnc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.luwei.testjusttalk.R;

public class CreateRoom extends AppCompatActivity {

    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);



        create=(Button) findViewById(R.id.button35);
        create.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateRoom.this,RoomNext.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
