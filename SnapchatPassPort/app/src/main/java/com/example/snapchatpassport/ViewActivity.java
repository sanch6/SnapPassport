package com.example.snapchatpassport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        CheckBox chB1 = (CheckBox) findViewById(R.id.cB1);
        CheckBox chB2 = (CheckBox) findViewById(R.id.cB2);
        CheckBox chB3 = (CheckBox) findViewById(R.id.cB3);
        Button btn = (Button)findViewById(R.id.bR);
        if(UserActivity.vRyerson) {
            chB1.setChecked(true);
        }
        if(UserActivity.vOpera) {
            chB2.setChecked(true);
        }
        if(UserActivity.vEiffel) {
            chB3.setChecked(true);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this, UserActivity.class));
            }
        });
    }
}
