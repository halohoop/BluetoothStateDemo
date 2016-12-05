package com.halohoop.bluetoothstatedemo;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "halohoop";
    private MyBluetoothReceiver myBluetoothReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        myBluetoothReceiver = new MyBluetoothReceiver();
        registerReceiver(myBluetoothReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myBluetoothReceiver != null) {
            unregisterReceiver(myBluetoothReceiver);
        }
    }

    class MyBluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                logi("huanghaiqidevice name: " + name);
                int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                switch (state) {
                    case BluetoothDevice.BOND_NONE:
                        //弹出的对话框按了取消 或者 配对失败（没有安配对或者允许按钮） 或者 直接关了蓝牙
                        logi("BOND_NONE 删除配对");
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        logi("BOND_BONDING 正在配对");//弹出对话框
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        logi("BOND_BONDED 配对成功");
                        break;
                }
            }
        }
    }

    public void logi(String s) {
        Log.i(TAG, "halohoop:" + s);
    }
}
