package com.example.sparelaptop3.xylockphone;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class MainMenu extends AppCompatActivity {

    PasswordManager pwmgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Instantiate password manager first so that any existing passwords can be retrieved
        pwmgr = new PasswordManager(this);
        Instrument i = new Xylophone();
        CharSequence chars = "ACDG";
        Password pass = new Password(i, chars);
        pwmgr.setPassword("test", pass);

        CreateUpdate.goToLockScreen(this);
        setContentView(R.layout.activity_main_menu);

        //Get a nice font
        TextView tx = (TextView)findViewById(R.id.appName);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/becca.ttf");

        tx.setTypeface(custom_font);
    }

    public void toCreate(View view) {
        Intent i = new Intent(this, CreateUpdate.class);
        i.putExtra("pwdmgr", pwmgr);
        startActivity(i);
    }

    public void toView(View view) {
        Intent i = new Intent(this, Display.class);
        i.putExtra("pwdmgr", pwmgr);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
