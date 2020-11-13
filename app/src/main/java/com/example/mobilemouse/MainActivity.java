package com.example.mobilemouse;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
@SuppressLint("SetTextI18n")

public class MainActivity extends AppCompatActivity {
    Thread Thread1 = null;
    EditText etIP, etPort;
    TextView tvMessages;
    EditText etMessage;
    Button btnSend;
    TextView mousePad;
    Context context;
    String SERVER_IP;
    int SERVER_PORT;

    private boolean isConnected=false;
    private boolean mouseMoved=false;
    private Socket socket;
    private PrintWriter out;

    private float initX =0;
    private float initY =0;
    private float disX =0;
    private float disY =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        Button btnleftclick = findViewById(R.id.btnleftclick);
        Button btnrightclick = findViewById(R.id.btnrightclick);
        Button btnConnect = findViewById(R.id.btnConnect);
        mousePad = (TextView)findViewById(R.id.mousePad);

        mousePad.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //save X and Y positions when user touches the TextView
                        initX = event.getX();
                        initY = event.getY();
                        mouseMoved = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        disX = event.getX() - initX; //Mouse movement in x direction
                        disY = event.getY() - initY; //Mouse movement in y direction
                            /*set init to new position so that continuous mouse movement
                            is captured*/
                        initX = event.getX();
                        initY = event.getY();
                        if (disX != 0 || disY != 0) {
                            String message;
                            message = disX + "," + disY;
                            new Thread(new Thread3(message)).start();
                            new Thread(new Thread1()).start();
//                                out.println(disX +","+ disY); //send mouse movement to server
                        }
                        mouseMoved = true;
                        break;
//                    case MotionEvent.ACTION_UP:
//                        //consider a tap only if usr did not move mouse after ACTION_DOWN
//                        if (!mouseMoved) {
//                            out.println(Constants.MOUSE_LEFT_CLICK);
//                        }
                }
                return true;
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SERVER_IP = etIP.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
                Thread1 = new Thread(new Thread1());
                Thread1.start();
            }
        });
        btnleftclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "left";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    new Thread(new Thread1()).start();
                }
            }
        });
        btnrightclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "right";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    new Thread(new Thread1()).start();
                }
            }
        });

    }
    private PrintWriter output;
    private BufferedReader input;
    class Thread1 implements Runnable {
        public void run() {
            Socket socket;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Thread3 implements Runnable {
        private String message;
        Thread3(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            output.write(message);
            output.flush();
        }
    }
}