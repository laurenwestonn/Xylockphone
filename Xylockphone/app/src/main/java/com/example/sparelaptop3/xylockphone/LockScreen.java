package com.example.sparelaptop3.xylockphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.EventListener;

public class LockScreen extends AppCompatActivity {
    private Intent callbackIntent;
    private Button callbackButton;
    public static String CALLBACK_INTENT = "CALLBACK_INTENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        Bundle extras = getIntent().getExtras();
        callbackIntent = (Intent) extras.get(CALLBACK_INTENT);
        Log.d("LockScreen", "onCreate");
        passwordCorrect();
    }

    public void passwordCorrect(){
        callbackIntent.putExtra("CALLBACK", "true");
        Log.d("LockScreen", "passwordCorrect");
        startActivity(callbackIntent);
    }

    private void passwordIncorrect(){

    }

    static void goToLockScreen(AppCompatActivity app){
        Bundle extras = app.getIntent().getExtras();
        if (extras == null || !extras.getString("CALLBACK").equals("true")) {
            Intent lockScreenIntent = new Intent(app, LockScreen.class);
            Intent callbackIntent = new Intent(app, app.getClass());
            lockScreenIntent.putExtra(LockScreen.CALLBACK_INTENT, callbackIntent);
            Log.d("LockScreenTest", "Going to lock screen");
            app.startActivity(lockScreenIntent);
        }
        if(extras != null) { extras.clear(); }
    }


}
