package com.test.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import java.lang.reflect.Method;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Admin on 23.10.2017.
 */

public class CallReceiver extends BroadcastReceiver  {
    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        final PhoneStateListener listener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state)
                {
                    case TelephonyManager.CALL_STATE_IDLE:
                        Toast.makeText(context.getApplicationContext(), "Call Ended..", Toast.LENGTH_SHORT).show();
                        Log.i("stop", "Call Ended....");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Toast.makeText(context.getApplicationContext(), "Call Picked..", Toast.LENGTH_SHORT) .show();
                        Log.i("received", "Call Picked....");

                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        // Вот здесь надо обрабатывать звонок
                        Toast.makeText(context.getApplicationContext(), "Call RingingBEOCH..." + incomingNumber,Toast.LENGTH_SHORT).show();

                        //autoPickCall(context);
                       try {
                            Runtime.getRuntime().exec("input keyevent " +
                                    Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
                        } catch (IOException e) {
                           Log.e("stop", "Call ringing", e);
                       }


                        break;
                }

            }
        };
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    private void autoPickCall(final Context mContext) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("input keyevent " +
                            Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
                } catch (IOException e) {
                    // Runtime.exec(String) had an I/O problem, try to fall back
                    String enforcedPerm = "android.permission.CALL_PRIVILEGED";
                    Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                    KeyEvent.KEYCODE_HEADSETHOOK));
                    Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                                    KeyEvent.KEYCODE_HEADSETHOOK));

                    mContext.sendOrderedBroadcast(btnDown, enforcedPerm);
                    mContext.sendOrderedBroadcast(btnUp, enforcedPerm);
                }
            }

        }).start();

    }


}
