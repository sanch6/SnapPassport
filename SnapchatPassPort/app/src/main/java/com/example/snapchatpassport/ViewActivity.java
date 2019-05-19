package com.example.snapchatpassport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Window;
import android.view.WindowManager;
public class ViewActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().hide(); // hide the title bar

        CheckBox chB1 = (CheckBox) findViewById(R.id.cB1);
        CheckBox chB2 = (CheckBox) findViewById(R.id.cB2);
        CheckBox chB3 = (CheckBox) findViewById(R.id.cB3);
        CheckBox chB4 = (CheckBox) findViewById(R.id.cB4);
        CheckBox chB5 = (CheckBox) findViewById(R.id.cB5);
        Button btn = (Button)findViewById(R.id.bR);
        if(UserActivity.vRyerson) {
            chB2.setChecked(true);
        }
        if(UserActivity.vOpera) {
            chB1.setChecked(true);
        }
        if(UserActivity.vEiffel) {
            chB3.setChecked(true);
        }
        if(UserActivity.vFuji) {
            chB4.setChecked(true);
        }
        if(UserActivity.vSquare) {
            chB5.setChecked(true);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this, UserActivity.class));
            }
        });
    }
}
