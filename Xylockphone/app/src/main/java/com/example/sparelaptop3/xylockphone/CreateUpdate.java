package com.example.sparelaptop3.xylockphone;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import org.billthefarmer.mididriver.MidiDriver;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.EventListener;

public class CreateUpdate extends AppCompatActivity /*implements View.OnTouchListener*/{

    private Intent callbackIntent;
    private Button callbackButton;
    public static String CALLBACK_INTENT = "CALLBACK_INTENT";
    private MidiSynthInterface midiSynth;
    private ImageButton buttonPlayNote;
    public static final String TAG = "YOUR-TAG-NAME";
    private Note A;
    private Note C;
    private Note D;
    private Note E;
    private Note F;
    private Note G;
    private String m_Text = "";
    private PasswordManager pwdmgr;
    private int state = 0; //0 idle, 1 playing, 2 recording, 3 lock
    private String appName;
    private boolean lockScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        callbackIntent = (Intent) extras.get(CALLBACK_INTENT);
        Log.d("LockScreen", "onCreate");
        lockScreen = (boolean) getIntent().getBooleanExtra("lockScreen", false);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update);
        Instrument i = (Instrument) getIntent().getSerializableExtra("instrument");
        CharSequence notes = getIntent().getCharSequenceExtra("notes");
        appName = getIntent().getStringExtra("app");
        if (lockScreen) {
            allowKeyPresses(true);
            setConf(true);
            state = 3;
        }
        else {
            allowKeyPresses(true);
            allowSaving(true);
        }
        //Get a nice font
        TextView tx = (TextView)findViewById(R.id.notesPlayed);
        if (notes != null) {
            StringBuilder str = new StringBuilder(String.valueOf(notes));
            int idx = str.length() - 1;

            while (idx > 0) {
                str.insert(idx, ",");
                idx = idx - 1;
            }

            tx.setText(str.toString());
        }
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/becca.ttf");
        tx.setTypeface(custom_font);

        ImageButton bA = (ImageButton) findViewById(R.id.XyA);
        ImageButton bC = (ImageButton) findViewById(R.id.XyC);
        ImageButton bD = (ImageButton) findViewById(R.id.XyD);
        ImageButton bE = (ImageButton) findViewById(R.id.XyE);
        ImageButton bF = (ImageButton) findViewById(R.id.XyF);
        ImageButton bG = (ImageButton) findViewById(R.id.XyG);

        View.OnClickListener declarePressedKey = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == 0 | state == 2 | state == 3) {   //Idle or Recording
                    //Find which note was played. The note is the third character
                    char strNote = getResources().getResourceEntryName(v.getId()).charAt(2);
                    Log.d(TAG, strNote + " was clicked");

                    playNote(strNote);

                    //Record/save this note
                    if(state == 2 || state == 3) {
                        TextView notesPlayed = (TextView) findViewById(R.id.notesPlayed);
                        notesPlayed.setText(notesPlayed.getText() + ((notesPlayed.getText().length() == 0) ? "" : ",") + strNote);
                    }
                }
            }
        };

        bA.setOnClickListener(declarePressedKey);
        bC.setOnClickListener(declarePressedKey);
        bD.setOnClickListener(declarePressedKey);
        bE.setOnClickListener(declarePressedKey);
        bF.setOnClickListener(declarePressedKey);
        bG.setOnClickListener(declarePressedKey);

        A = new Note((byte) 0x45);
        C = new Note((byte) 0x3C);
        D = new Note((byte) 0x3E);
        E = new Note((byte) 0x40);
        F = new Note((byte) 0x41);
        G = new Note((byte) 0x43);

        buttonPlayNote = (ImageButton)findViewById(R.id.XyA);
        //buttonPlayNote.setOnTouchListener(this);

        midiSynth = new MidiSynthInterface();

        PasswordManager pwdmgr = (PasswordManager) getIntent().getSerializableExtra("pwdmgr");
        if (pwdmgr == null) {
            pwdmgr = new PasswordManager(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        midiSynth.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        midiSynth.stop();
    }

    public void playNote(char noteChar) {
        switch (noteChar) {
            case 'A': midiSynth.playNote(A);
                break;
            case 'C': midiSynth.playNote(C);
                break;
            case 'D': midiSynth.playNote(D);
                break;
            case 'E': midiSynth.playNote(E);
                break;
            case 'G': midiSynth.playNote(G);
                break;
            case 'F': midiSynth.playNote(F);
                break;
        }
    }

    //=================================BUTTON STATE METHODS===========================
    // 0 - Idle                        1 - Playing                          2 - Recording
    //BUTTON CLICK METHODS=========
    public void onPlayButton(View v) {
        if (state == 0 || state == 3)
            setPlay(v); //Start music, disallow key presses, retain pw
        else if (state == 1) {
            setIdle(); //Stop music, allow key presses, retain pw
        }
    }

    public void onRecordButton(View v) {
        if (state == 0)
            setRecord(v);   //Stop music if playing, allow key presses, append to PW
        else if (state == 2) {
            setIdle();     //Keep music off, allow key presses, retain pw
        }
        else if (state == 3) {
            passwordCorrect();
           /* CharSequence notes = ((TextView) findViewById(R.id.notesPlayed)).getText();
            if (pwdmgr.equals(((TextView) findViewById(R.id.notesPlayed)).getText())) {
                passwordCorrect();
            }
            else {
                passwordIncorrect();
            }*/
        }
    }

    public void onSaveButton(View v) {
        if (state == 3)
            ((TextView) findViewById(R.id.notesPlayed)).setText("");
        else
        //You can only click the save button if you're allowed to save
        setSave(v);
    }

    //STATE METHODS=========
    public void setPlay(View v) {
        playMusic(true);
        setIdle();
    }

    public void setConf(boolean b) {
        findViewById(R.id.record).setBackgroundResource(R.drawable.icon_confirm);
        findViewById(R.id.save).setBackgroundResource(R.drawable.icon_cancel);
        //Delete the pw you have saved - DO THIS PROPERLY, delete from file
    }

    public void setRecord(View v) {
        state = 2;

        allowKeyPresses(true);

        allowSaving(false);

        // append to PW

        //Change icon to stop
        v.setBackgroundResource(R.drawable.icon_stop);
        //Disable play - make it unclickable with no image
        findViewById(R.id.play).setBackgroundResource(0);
        findViewById(R.id.play).setEnabled(false);

        //Delete the pw you have saved - DO THIS PROPERLY, delete from file
        ((TextView) findViewById(R.id.notesPlayed)).setText("");
    }

    public void setIdle() {
        if (state != 3) {
            allowKeyPresses(true);

            allowSaving(true);

            //Allow to play
            findViewById(R.id.play).setEnabled(true);
            findViewById(R.id.play).setBackgroundResource(R.drawable.icon_play);
            //Allow to record
            findViewById(R.id.record).setBackgroundResource(R.drawable.icon_record);

            state = 0;
        }
    }

    public void setSave(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Password Name");

        // Set up the input
        final EditText input = new EditText(this);
        input.setText(appName);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if (m_Text.length() > 0) {
                    CharSequence notes = ((TextView) findViewById(R.id.notesPlayed)).getText();
                    notes = notes.toString().replace(",","").replace(" ", "");
                    Instrument i = new Xylophone();
                    Password p = new Password(i, notes);
                    pwdmgr.setPassword(m_Text, p);

                    Toast toast = Toast.makeText(CreateUpdate.this, m_Text + " has been " + (appName != null ? "updated" : "created"), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    //HELPER METHODS===============
    public void allowKeyPresses(boolean b) {
        //Decides whether to allow you to press the xylophone keys
        findViewById(R.id.XyA).setEnabled(b);
        findViewById(R.id.XyC).setEnabled(b);
        findViewById(R.id.XyD).setEnabled(b);
        findViewById(R.id.XyE).setEnabled(b);
        findViewById(R.id.XyF).setEnabled(b);
        findViewById(R.id.XyG).setEnabled(b);
    }

    public void playMusic (boolean b) {
        if(!b)
            return;

        if (state != 3) {
            state = 1;

            //Music is going to play. Change icon to stop
            findViewById(R.id.play).setBackgroundResource(R.drawable.icon_stop);
            allowKeyPresses(false);
            allowSaving(false);
        }
        CharSequence notesPlayed = ((TextView) findViewById(R.id.notesPlayed)).getText();
        for (int index = 0; index < notesPlayed.length(); index+=2) {
            playNote(notesPlayed.charAt(index));
            try {
                sleep(250);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (state != 3) {
            //Music finished. Change icon to play
            findViewById(R.id.play).setBackgroundResource(R.drawable.icon_play);
            allowKeyPresses(true);
            allowSaving(true);
        }
    }

    public void allowSaving(boolean b) {
        //Decides whether to display the button's image and whether it is enabled
        findViewById(R.id.save).setBackgroundResource((b ? R.drawable.icon_save : 0));
        findViewById(R.id.save).setEnabled(b);
    }
    //==========================BUTTON STATE METHODS END===========================

    public void passwordCorrect(){
        callbackIntent.putExtra("CALLBACK", "true");
        Log.d("LockScreen", "passwordCorrect");
        startActivity(callbackIntent);
    }

    private void passwordIncorrect(){

    }

    static void goToLockScreen(AppCompatActivity app) {
        Bundle extras = app.getIntent().getExtras();
        if (extras == null || !extras.getString("CALLBACK").equals("true")) {
            Intent lockScreenIntent = new Intent(app, CreateUpdate.class);
            lockScreenIntent.putExtra("lockScreen", true);
            Intent callbackIntent = new Intent(app, app.getClass());
            lockScreenIntent.putExtra(LockScreen.CALLBACK_INTENT, callbackIntent);
            Log.d("LockScreenTest", "Going to lock screen");
            app.startActivity(lockScreenIntent);
        }
        if (extras != null) {
            extras.clear();
        }
    }
}
