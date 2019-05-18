package com.example.snapchatpassport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserActivity extends AppCompatActivity {
    public static boolean vRyerson = false, vOpera = false, vEiffel = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Button btn = (Button)findViewById(R.id.bV);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( UserActivity.this,ViewActivity.class));
            }
        });
        Button btn2 = (Button)findViewById(R.id.bC);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( UserActivity.this,CheckInActivity.class));
            }
        });
        Button btn3 = (Button)findViewById(R.id.bLO);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vRyerson = false; vOpera = false; vEiffel = false;
                startActivity(new Intent( UserActivity.this,MainActivity.class));
            }
        });
    }
}
