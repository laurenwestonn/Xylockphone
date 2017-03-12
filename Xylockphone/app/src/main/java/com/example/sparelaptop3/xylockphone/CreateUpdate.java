package com.example.sparelaptop3.xylockphone;

import android.content.DialogInterface;
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
import static java.lang.Thread.sleep;
import java.util.HashMap;

public class CreateUpdate extends AppCompatActivity /*implements View.OnTouchListener*/{

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
    private int state = 0; //0 idle, 1 playing, 2 recording

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update);

        //Get a nice font
        TextView tx = (TextView)findViewById(R.id.notesPlayed);
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
                if(state == 0 | state == 2) {   //Idle or Recording
                    //Find which note was played. The note is the third character
                    char strNote = getResources().getResourceEntryName(v.getId()).charAt(2);
                    Log.d(TAG, strNote + " was clicked");

                    playNote(strNote);

                    //Record/save this note
                    if(state == 2) {
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        midiSynth.start();

        // Get the configuration.
     /*   config = midiDriver.config();

        // Print out the details.
        Log.d(this.getClass().getName(), "maxVoices: " + config[0]);
        Log.d(this.getClass().getName(), "numChannels: " + config[1]);
        Log.d(this.getClass().getName(), "sampleRate: " + config[2]);
        Log.d(this.getClass().getName(), "mixBufferSize: " + config[3]);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        midiSynth.stop();
    }

  /*  @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.d(this.getClass().getName(), "Motion event: " + event);

        if (v.getId() == R.id.XyA) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d(this.getClass().getName(), "MotionEvent.ACTION_DOWN");
                midiSynth.playNote(A);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d(this.getClass().getName(), "MotionEvent.ACTION_UP");
                midiSynth.stopNote(A);
            }
        }

        return false;
    }*/

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
        if (state == 0)
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
    }

    public void onSaveButton(View v) {
        //You can only click the save button if you're allowed to save
        setSave(v);
    }

    //STATE METHODS=========
    public void setPlay(View v) {
        playMusic(true);
        setIdle();
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
        allowKeyPresses(true);

        allowSaving(true);

        //Allow to play
        findViewById(R.id.play).setEnabled(true);
        findViewById(R.id.play).setBackgroundResource(R.drawable.icon_play);
        //Allow to record
        findViewById(R.id.record).setBackgroundResource(R.drawable.icon_record);

        state = 0;
    }

    public void setSave(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Password Name");

        // Set up the input
        final EditText input = new EditText(this);
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

        state = 1;

        //Music is going to play. Change icon to stop
        findViewById(R.id.play).setBackgroundResource(R.drawable.icon_stop);
        allowKeyPresses(false);
        allowSaving(false);

        CharSequence notesPlayed = ((TextView) findViewById(R.id.notesPlayed)).getText();
        for (int index = 0; index < notesPlayed.length(); index+=2) {
            playNote(notesPlayed.charAt(index));
            try {
                sleep(250);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Music finished. Change icon to play
        findViewById(R.id.play).setBackgroundResource(R.drawable.icon_play);
        allowKeyPresses(true);
        allowSaving(true);
    }

    public void allowSaving(boolean b) {
        //Decides whether to display the button's image and whether it is enabled
        findViewById(R.id.save).setBackgroundResource((b ? R.drawable.icon_save : 0));
        findViewById(R.id.save).setEnabled(b);
    }
    //==========================BUTTON STATE METHODS END===========================
}
