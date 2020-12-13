package com.example.myapplication3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    Button button4;
    EditText start, finish;
    private Socket socket;
    private Handler mHandler;
    final Context context = this;
    byte line;

    DataOutputStream dos;
    DataInputStream dis;

    private String ip = "192.168.1.103"; // IP 번호
    private int port = 9999; // port 번호

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bushelp);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);// layout xml 과 자바파일을 연결
        mHandler = new Handler();
        start = findViewById(R.id.start);
        finish = findViewById(R.id.finish);
    } // end onCreate()

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button4:
                connect();
                /*
                final CharSequence[] items = {"사과", "딸기", "오렌지", "수박"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // 제목셋팅
                alertDialogBuilder.setTitle("선택 목록 대화 상자");
                alertDialogBuilder.setItems(items,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                // 프로그램을 종료한다
                                Toast.makeText(getApplicationContext(),
                                        items[id] + " 선택했습니다.",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
                break;
                */
        } // end MyTwo
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

                Log.w("edit 넘어가야 할 값 : ", "안드로이드에서 서버로 연결요청");

                try {
                    dos = new DataOutputStream(socket.getOutputStream());// output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream()); // input에 받을꺼 넣어짐
                    String msg ="Bus:" + start.getText().toString() + ":" + finish.getText().toString();
                    System.out.print(msg);
                    dos.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼", "버퍼생성 잘됨");

// 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    while (true) {
                        line = dis.readByte();
                        Log.w("서버에서 받아온 값 ", "" + line);
                    }
                } catch (Exception e) {

                }
            }
        };
// 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }// end onCreate()
}