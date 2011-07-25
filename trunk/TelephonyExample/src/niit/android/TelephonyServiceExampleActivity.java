package niit.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class TelephonyServiceExampleActivity extends Activity{
    private static final String TAG="TelephonyServiceDemo"; 
    @Override 
    protected void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState); 
 
        TelephonyManager teleMgr =  (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
        teleMgr.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE); 
    } 
 
    class MyPhoneStateListener extends PhoneStateListener 
    {
        @Override 
        public void onCallStateChanged(int state, String incomingNumber) { 
            super.onCallStateChanged(state, incomingNumber); 
            Toast toast = Toast.makeText(TelephonyServiceExampleActivity.this, "", 3000);
            switch(state) 
            { 
 
                case TelephonyManager.CALL_STATE_IDLE: 
                    Log.d(TAG, "call state idle...incoming number is["+ 
incomingNumber+"]");break;
                case TelephonyManager.CALL_STATE_RINGING: 
                    Log.d(TAG, "call state ringing...incoming number is["+ 
incomingNumber+"]");break; 
                case TelephonyManager.CALL_STATE_OFFHOOK: 
                    Log.d(TAG, "call state Offhook...incoming number is["+ 
incomingNumber+"]");break; 
                default: 
                    Log.d(TAG, "call state ["+state+"]incoming number is["+ 
incomingNumber+"]");break; 
            } 
        }
    }
}
