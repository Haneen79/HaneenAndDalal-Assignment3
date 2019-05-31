package com.example.cct.lecc2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        ImageView im;
        int level;
        TextView charging;
        Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        charging = findViewById(R.id.textView2);
        TextView per = findViewById(R.id.textView3);
        im = findViewById(R.id.imageView);


        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        Intent i = registerReceiver(receiver,filter);

        BatteryManager manager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        level = i.getIntExtra(manager.EXTRA_LEVEL,0);
        per.setText(level+"%");

        //Toast.makeText(this, p+"..", Toast.LENGTH_SHORT).show();


    }
    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
                flag = true;

            }else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
                flag = false;

            }else if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                if (flag){
                    charging.setText("On");
                    if (level<20){
                        im.setImageResource(R.drawable.ic_battery_charging_20_black_24dp);
                    }else if (level <50){
                        im.setImageResource(R.drawable.ic_battery_charging_50_black_24dp);
                    }else if (level<90){
                        im.setImageResource(R.drawable.ic_battery_charging_80_black_24dp);
                    }else{
                        im.setImageResource(R.drawable.ic_battery_charging_full_black_24dp);
                    }
                }else {
                    charging.setText("off");
                    if (level<20){
                        im.setImageResource(R.drawable.ic_battery_20_black_24dp);
                    }else if (level <50){
                        im.setImageResource(R.drawable.ic_battery_50_black_24dp);
                    }else if (level<90){
                        im.setImageResource(R.drawable.ic_battery_80_black_24dp);
                    }else{
                        im.setImageResource(R.drawable.ic_battery_full_black_24dp);
                    }
                }
            }
            IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
            LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                    String networkStatus = isNetworkAvailable ? "connected" : "disconnected";

                    Snackbar.make(findViewById(R.id.activity_main), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
                }
            }, intentFilter);
        }
    }
        }

    }
}
