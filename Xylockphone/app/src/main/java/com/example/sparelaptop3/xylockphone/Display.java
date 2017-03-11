package com.example.sparelaptop3.xylockphone;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;

public class Display extends AppCompatActivity {

    private static String DEBUG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        PasswordManager pwdmgr = (PasswordManager) getIntent().getSerializableExtra("pwdmgr");
        HashMap<String, Password> passwords = pwdmgr.getAll();
        for (HashMap.Entry<String, Password> password : passwords.entrySet())
        {
            String appName = password.getKey();
            Password appPass = password.getValue();
            Instrument instrument = appPass.getInstrument();
            Class type = instrument.getClass();
            String className = type.toString().split("com.example.sparelaptop3.xylockphone.")[1];
            Log.d(DEBUG, className);
        }
    }
}
