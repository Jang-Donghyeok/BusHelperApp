package com.example.myapplication3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    EditText et_id, et_pass, et_passck, et_name, et_age, et_hak, et_maj;
    Button btn_register;
    private String html = "";
    private Handler mHandler;
    private Socket socket;

    DataOutputStream dos;
    DataInputStream dis;

    private String ip = "10.0.2.2"; // IP 번호
    private int port = 9999; // port 번호
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);  // layout xml 과 자바파일을 연결
        btn_register = (Button)findViewById(R.id.btn_register);;
        btn_register.setOnClickListener(this);
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_passck = findViewById(R.id.et_passck);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_hak = findViewById(R.id.et_hak);
        et_maj = findViewById(R.id.et_maj);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register: // ip 받아오는 버튼
                connect();
        }
    }
    public void connect(){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");
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

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결요청");

                try {
                    dos = new DataOutputStream(socket.getOutputStream());// output에 보낼꺼 넣음
                    String msg= et_id.getText().toString() +","+et_pass.getText().toString()+","+et_passck.getText().toString() +","+et_name.getText().toString()
                            +","+et_age.getText().toString()+","+et_hak.getText().toString()+","+et_maj.getText().toString();
                    dis = new DataInputStream(socket.getInputStream()); // input에 받을꺼 넣어짐
                    dos.writeUTF(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

// 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    String line = "";
                    int line2;
                    while(true) {
                        line = (String)dis.readUTF();
                        line2 = (int)dis.read();
                        Log.w("서버에서 받아온 값 ",""+line);
                        Log.w("서버에서 받아온 값 ",""+line2);
                    }
                }catch (Exception e){

                }
            }
        };
// 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }// end onCreate()
} // end MyTwo

