package com.example.sparelaptop3.xylockphone;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.logging.Logger;

public class CreateUpdate extends AppCompatActivity {

    public static final String TAG = "YOUR-TAG-NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update);
    }

    public void declarePressedNote (View view) {

        //Get the name of the key that was pressed e.g. XyA
        //Button btnKey = (Button) view;
        //String buttonName = btnKey.getText().toString();

        //Remove this cheat to make the code work, need to actually get the name of the button clicked
        String buttonName = "XyA";

        //Convert the name of the key to be the name of the note
        //e.g. A... get the third character, this is the note name
        char strNote = buttonName.charAt(2);

        //Print out/log which one was pressed
        Log.d(TAG, strNote + " was clicked");

    }
}
