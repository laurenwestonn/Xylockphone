
package com.example.sparelaptop3.xylockphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.HashMap;

public class Display extends AppCompatActivity {

    private static String DEBUG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        LinearLayout DisplayLayout = (LinearLayout) findViewById(R.id.DisplayLayout);
        PasswordManager pwdmgr = (PasswordManager) getIntent().getSerializableExtra("pwdmgr");
        HashMap<String, Password> passwords = pwdmgr.getAll();
        int size = passwords.size();
        final String[] Apps = new String[size];
        Integer[] instrumentIcons = new Integer[size];
        final CharSequence[] Notes = new CharSequence[size];
        final Instrument[] Instrument = new Instrument[size];
        int i = 0;
        for (HashMap.Entry<String, Password> password : passwords.entrySet()) {
            String appName = password.getKey();
            Password appPass = password.getValue();
            Instrument instrument = appPass.getInstrument();
            Class type = instrument.getClass();
            CharSequence noteSequence = appPass.getNotes();
            String className = type.toString().split("com.example.sparelaptop3.xylockphone.")[1];
            Log.d(DEBUG, className);
            int d = 0;
            switch (className) {
                case "Xylophone":
                    d = R.drawable.xylkey;
                    break;
            }
   //         d = R.drawable.guitar;
            Apps[i] = appName;
            instrumentIcons[i] = d;
            Notes[i] = noteSequence;
            Instrument[i] = instrument;
            i++;
        }
        ImageList il = new ImageList(Display.this, Apps, instrumentIcons);
        ListView ilList = (ListView) findViewById(R.id.PasswordList);
        ilList.setAdapter(il);
        ilList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Intent i = new Intent(Display.this, CreateUpdate.class);
                i.putExtra("app", Apps[position]);
                i.putExtra("notes", Notes[position]);
                i.putExtra("instrument", Instrument[position]);
                startActivity(i);
            }
        });
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