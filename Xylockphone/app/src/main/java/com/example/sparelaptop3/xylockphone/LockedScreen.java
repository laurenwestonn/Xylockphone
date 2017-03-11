package com.example.sparelaptop3.xylockphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SPARELAPTOP1 on 11/03/2017.
 */

public class LockedScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view);
        startActivity(new Intent(this, LockScreen.class));
    }

    void toLockScreen(){

        LockScreen lockScreen = new LockScreen(new Intent(this, LockScreen.class));
    }

}
