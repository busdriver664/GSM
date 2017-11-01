package com.test.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
      /* Intent serviceIntent = new Intent(this, MyPhoneStateListener.class);
        startService(serviceIntent);*/
      /*TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener listener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state)
                {
                    case TelephonyManager.CALL_STATE_IDLE:
                        Toast.makeText(getApplicationContext(), "Call Ended..", Toast.LENGTH_LONG).show();
                        Log.i("stop", "Call Ended....");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Toast.makeText(getApplicationContext(), "Call Picked..", Toast.LENGTH_LONG) .show();
                        //Log.i("received", "Call Picked....");

                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        // Вот здесь надо обрабатывать звонок
                        Toast.makeText(getApplicationContext(), "Call Ringing.." + incomingNumber,Toast.LENGTH_LONG).show();
                        try {
                            Runtime.getRuntime().exec("input keyevent " +
                                    Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));

                        } catch (IOException e) {

                        }
                        break;
                }

            }
        };
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);*/

    }

    private void initializeView() {
        screen = (EditText)findViewById(R.id.screen);
        int idList [] = {R.id.btn0, R.id.btn1, R.id.btn2,R.id.btn3,
                R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,R.id.btn10,R.id.btn11,
                                R.id.btnN,R.id.btnDial,R.id.btnDel};
        for (int d: idList){
            View v = (View)findViewById(d);
            v.setOnClickListener(this);
        }
    }

    public void display(String val){
        screen.append(val);
    }

   private boolean checkCallPermission(){
        String permission = "android.permission.CALL_PHONE";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn0:
                display("0");
                break;
            case R.id.btn1:
                display("1");
                break;
            case R.id.btn2:
                display("2");
                break;
            case R.id.btn3:
                display("3");
                break;
            case R.id.btn4:
                display("4");
                break;
            case R.id.btn5:
                display("5");
                break;
            case R.id.btn6:
                display("6");
                break;
            case R.id.btn7:
                display("7");
                break;
            case R.id.btn8:
                display("8");
                break;
            case R.id.btn9:
                display("9");
                break;
            case R.id.btn10:
                display("#");
                break;
            case R.id.btn11:
                display("*");
                break;
            case R.id.btnDial:
                if(screen.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter some digits",Toast.LENGTH_SHORT).show();
                else if (checkCallPermission())
                   startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ screen.getText())));
                break;
            case R.id.btnDel:
                if(screen.getText().toString().length()>=1) {
                    String newScreen = screen.getText().toString().substring(0, screen.getText().toString().length() - 1);
                    screen.setText(newScreen);
                }
                break;
            case R.id.btnN:
                display("-");
                break;
            default:
                break;
        }
    }


}


