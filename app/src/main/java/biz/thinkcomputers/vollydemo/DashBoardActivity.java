package biz.thinkcomputers.vollydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DashBoardActivity extends AppCompatActivity {

    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent recieveIntent = getIntent();
        String new_name = recieveIntent.getStringExtra("abc");

        name = (TextView) findViewById(R.id.name);
        name.setText(new_name);
    }
}
