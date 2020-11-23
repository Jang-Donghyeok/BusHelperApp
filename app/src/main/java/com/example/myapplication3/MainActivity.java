package com.example.myapplication3;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    EditText et_id, et_pass;
    TextView idtext;
    Button btn_login;
    private Handler mHandler;
    private Socket socket;
    String line;

    DataOutputStream dos;
    DataInputStream dis;

    private String ip = "10.0.2.2"; // IP 번호
    private int port = 9999; // port 번호

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHandler = new Handler();
        idtext = (TextView) findViewById(R.id.et_id);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        Button b = (Button) findViewById(R.id.btn_register);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: // ip 받아오는 버튼
                connect();
                if (line == "Login OK"){
                    Intent intent = new Intent(
                            getApplicationContext(), // 현재 화면의 제어권자
                            MainActivity3.class); // 다음 넘어갈 클래스 지정
                    startActivity(intent);
                }
        } 
    }

    public void connect() {
        mHandler = new Handler();
        Log.w("connect", "연결 하는중");
// 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {

// 서버 접속
                try {
                    socket = new Socket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }
                try {
                    dos = new DataOutputStream(socket.getOutputStream());// output에 보낼꺼 넣음
                    String msg = "login:"+et_id.getText().toString() + ":" + et_pass.getText().toString();
                    System.out.println(msg);
                    dis = new DataInputStream(socket.getInputStream()); // input에 받을꺼 넣어짐
                    dos.writeUTF(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                try {
                    int line2;
                    while (true) {
                        line = (String) dis.readUTF();
                        Log.w("서버에서 받아온 값 ", "" + line);
                    }
                } catch (Exception e) {

                }
            }
        };
        checkUpdate.start();
    }
}
