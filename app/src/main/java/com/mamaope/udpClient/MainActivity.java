package com.mamaope.udpClient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSendMessage, btnReceiveMessage, btnMockMamaOpeDevice;
        btnReceiveMessage = findViewById(R.id.btn_home);
        btnSendMessage = findViewById(R.id.btn_send_data);
        btnMockMamaOpeDevice = findViewById(R.id.btn_mock_mamaope_device);

        btnSendMessage.setOnClickListener(this);
        btnReceiveMessage.setOnClickListener(this);
        btnMockMamaOpeDevice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                startActivity(new Intent(MainActivity.this, ReceiveDataActivity.class));
                break;
            case R.id.btn_send_data:
                startActivity(new Intent(MainActivity.this, SendDataActivity.class));
                break;
            case R.id.btn_mock_mamaope_device:
                startActivity(new Intent(MainActivity.this, MockMamaOpeDeviceActivity.class));
                break;
        }
    }
}