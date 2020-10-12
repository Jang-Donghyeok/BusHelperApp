package com.example.myapplication3;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView idtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_login);
        idtext = (TextView)findViewById(R.id.et_id);

        Button b = (Button)findViewById(R.id.btn_register);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        MainActivity2.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });
    }

    public void on1 (View v){
        Toast.makeText(getApplicationContext(),idtext.getText().toString() + "님이 로그인 하셨습니다.",Toast.LENGTH_SHORT).show();
    }
    public void on2 (Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_register);
    }
}