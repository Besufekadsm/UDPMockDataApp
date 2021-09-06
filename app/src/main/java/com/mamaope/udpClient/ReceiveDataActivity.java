package com.mamaope.udpClient;

import static com.mamaope.udpClient.Constants.MY_IP_PORT;
import static com.mamaope.udpClient.Constants.SEND_RECEIVER_IP_PORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceiveDataActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnHome, btnReceiveMessage;
    TextView tvOutputMessage;
    TextInputEditText etMyPort;
    int myPort = MY_IP_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_activity);

        btnReceiveMessage = findViewById(R.id.btn_receive_data);
        btnHome = findViewById(R.id.btn_home);
        etMyPort = findViewById(R.id.et_my_port);

        tvOutputMessage = findViewById(R.id.tv_udp_received);

        btnHome.setOnClickListener(this);
        btnReceiveMessage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                startActivity(new Intent(ReceiveDataActivity.this, MainActivity.class));
                break;
            case R.id.btn_receive_data:
                if (!etMyPort.getText().toString().isEmpty()) {
                    myPort = Integer.parseInt(etMyPort.getText().toString());
                }
                Log.e("My Port : ", ""+myPort);
                new Thread(new ClientListen()).start();
                break;
        }
    }

    public class ClientListen implements Runnable {
        @Override
        public void run() {
            boolean run = true;
            DatagramSocket udpSocket = null;
            try {
                udpSocket = new DatagramSocket(myPort);

                while (run) {
                    try {

                        byte[] message = new byte[8000];
                        DatagramPacket packet = new DatagramPacket(message, message.length);
                        Log.i("UDP client: ", "about to wait to receive");
                        udpSocket.receive(packet);
                        String text = new String(message, 0, packet.getLength());
                        tvOutputMessage.append("Recieved : " + text);
                    } catch (IOException e) {
                        Log.e("UDP IOException", "error: ", e);
                        run = false;
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    /*public class ClientSendAndListen implements Runnable {
        @Override
        public void run() {
            boolean run = true;
            try {
                DatagramSocket udpSocket = new DatagramSocket(SEND_RECEIVER_IP_PORT);
                InetAddress serverAddr = InetAddress.getByName(SEND_RECEIVER_IP_ADDRESS);
                byte[] buf = ("FILES").getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, MY_IP_PORT);
                udpSocket.send(packet);
                while (run) {
                    try {
                        byte[] message = new byte[8000];
                        DatagramPacket packet = new DatagramPacket(message,message.length);
                        Log.i("UDP client: ", "about to wait to receive");
                        udpSocket.setSoTimeout(10000);
                        udpSocket.receive(packet);
                        String text = new String(message, 0, p.getLength());
                        Log.d("Received text", text);
                    } catch (IOException e) {
                        Log.e(" UDP client has IOException", "error: ", e);
                        run = false;
                        udpSocket.close();
                    } catch (SocketTimeoutException e) {
                        Log.e("Timeout Exception","UDP Connection:",e);
                        run = false;
                        udpSocket.close();
                    }
                }
            } catch (SocketException | UnknownHostException e) {
                Log.e("Socket Open:", "Error:", e);
            }
        }
    }*/

}