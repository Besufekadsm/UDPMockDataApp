package com.mamaope.udpClient;

import static com.mamaope.udpClient.Constants.SEND_RECEIVER_IP_ADDRESS;
import static com.mamaope.udpClient.Constants.SEND_RECEIVER_IP_PORT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SendDataActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSendMessage, btnHome;
    TextInputEditText etMessage,etDestinationPort,etDestinationIP;
    TextView tvOutputMessage;
    String message,destinationIP;
    int destinationPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_activity);
        destinationIP = SEND_RECEIVER_IP_ADDRESS;
        destinationPort = SEND_RECEIVER_IP_PORT;
        btnHome = findViewById(R.id.btn_home);
        btnSendMessage = findViewById(R.id.btn_send_data);
        tvOutputMessage = findViewById(R.id.tv_udp_received);
        etMessage = findViewById(R.id.et_message);
        etDestinationIP = findViewById(R.id.et_destination_ip);
        etDestinationPort = findViewById(R.id.et_destination_port);

        btnSendMessage.setOnClickListener(this);
        btnHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                startActivity(new Intent(SendDataActivity.this,MainActivity.class));
                break;
            case R.id.btn_send_data:
                message = etMessage.getText().toString();
                if (!etDestinationPort.getText().toString().isEmpty()) {
                    destinationPort=Integer.getInteger(etDestinationPort.getText().toString());
                }
                if (!etDestinationIP.getText().toString().isEmpty()) {
                    destinationIP = etDestinationIP.getText().toString();
                }

                new Thread(new ClientSend()).start();
                break;
        }
    }

    public class ClientSend implements Runnable {
        @Override
        public void run() {
            try {
                DatagramSocket udpSocket = new DatagramSocket();
                InetAddress serverAddr = InetAddress.getByName(destinationIP);
                byte[] buf = (message).getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, destinationPort);
                tvOutputMessage.append("\n Sending : " + message);
                udpSocket.send(packet);
            } catch (SocketException e) {
                Log.e("Udp:", "Socket Error:", e);
            } catch (IOException e) {
                Log.e("Udp Send:", "IO Error:", e);
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
    private void sendAudioFile() {

    }
}