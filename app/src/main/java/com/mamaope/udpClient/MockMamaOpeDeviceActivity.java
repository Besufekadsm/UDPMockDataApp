package com.mamaope.udpClient;

import static com.mamaope.udpClient.Constants.SEND_RECEIVER_IP_ADDRESS;
import static com.mamaope.udpClient.Constants.SEND_RECEIVER_IP_PORT;
import static com.mamaope.udpClient.Constants.sample;

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

public class MockMamaOpeDeviceActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMockMamaOpeData, btnHome;
    TextView tvOutputMessage;
    TextInputEditText etDestinationPort,etDestinationIP;
    String message, destinationIP;
    int destinationPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_mama_ope_device);
        destinationIP = SEND_RECEIVER_IP_ADDRESS;
        destinationPort = SEND_RECEIVER_IP_PORT;
        btnHome = findViewById(R.id.btn_home);
        btnMockMamaOpeData = findViewById(R.id.btn_mock_mamaope_device);
        tvOutputMessage = findViewById(R.id.tv_udp_received);
        etDestinationIP = findViewById(R.id.et_destination_ip);
        etDestinationPort = findViewById(R.id.et_destination_port);

        btnMockMamaOpeData.setOnClickListener(this);
        btnHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                startActivity(new Intent(MockMamaOpeDeviceActivity.this, MainActivity.class));
                break;
            case R.id.btn_mock_mamaope_device:
                if (!etDestinationPort.getText().toString().isEmpty()) {
                    destinationPort=Integer.parseInt(etDestinationPort.getText().toString());
                }
                if (!etDestinationIP.getText().toString().isEmpty()) {
                    destinationIP = etDestinationIP.getText().toString();
                }
                new Thread(new MockDeviceData()).start();
                break;
        }
    }

    public class MockDeviceData implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= sample.length; i++) {
                try {
                    if(i == sample.length){
                        i=0;
                    }
                    DatagramSocket udpSocket = new DatagramSocket();
                    InetAddress serverAddr = InetAddress.getByName(destinationIP);
                    message =  sample[i];
                    byte[] buf = (message).getBytes();
                    //Log.e("Destination IP : ",""+ destinationIP);
                    //Log.e("Destination Port : ",""+ destinationPort);
                    //Log.e("Buffer Length",""+ buf.length);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, destinationPort);
                    //tvOutputMessage.append("\n Sending : " + message);
                    //Log.i("Sending : " , message);
                    udpSocket.send(packet);
                } catch (SocketException e) {
                    Log.e("Udp:", "Socket Error:", e);
                } catch (IOException e) {
                    Log.e("Udp Send:", "IO Error:", e);
                }
            }
        }
    }
}