package com.test.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.IOException;

public class MyPhoneStateListener extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StateListener phoneStateListener = new StateListener();
        TelephonyManager telephonymanager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        telephonymanager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    class StateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(getApplicationContext(), "Call RingingAAAAA..." + incomingNumber,Toast.LENGTH_LONG).show();
                    try {
                        Runtime.getRuntime().exec("input keyevent " +
                                Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));

                    } catch (IOException e) {

                    }
                    //Автоответ на любой входящий звонок
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(getApplicationContext(), "Call Picked..", Toast.LENGTH_LONG) .show();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Toast.makeText(getApplicationContext(), "Call Ended..", Toast.LENGTH_LONG).show();
                    Log.i("stop", "Call Ended....");
                    break;
            }
        }


    };

    @Override
    public void onDestroy() {

    }
}


