package com.example.theappguruz.phonestatereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.logging.Handler;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by theappguruz on 07/05/16.
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        try {
            SharedPreferences pref = context.getSharedPreferences("callapppref", 0);
            SharedPreferences.Editor editor = pref.edit();

            System.out.println("Receiver start");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Toast.makeText(context, "Incoming Call State", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Ringing State Number is -" + incomingNumber, Toast.LENGTH_SHORT).show();

                editor.putBoolean("call_received", false);
                editor.commit();
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
                Toast.makeText(context, "Call Received State", Toast.LENGTH_SHORT).show();

                editor.putBoolean("call_received", true);
                editor.commit();


            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Toast.makeText(context, "Call Idle State", Toast.LENGTH_SHORT).show();
                /*Intent scheduledIntent = new Intent(context.getPackageName(), PopupActivity.class);
                scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(scheduledIntent);*/

                if (pref.getBoolean("call_received", false) == true) {

                    // call received and disconnects
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intentone = new Intent(context.getApplicationContext(), PopupActivity.class);
                            intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentone);
                        }
                    }, 2000);
                } else {

                    // without receiving call, it disconnects


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

