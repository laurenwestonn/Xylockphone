package com.example.sparelaptop3.xylockphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

/**
 * Created by SPARELAPTOP1 on 11/03/2017.
 */

public class LockScreen extends AppCompatActivity {
    public static final int LOCK = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPasswordCorrect();
    }

    private void onPasswordCorrect(){
        finishActivity(LOCK);
    }

    private void onPasswordIncorrect(){

    }
}
