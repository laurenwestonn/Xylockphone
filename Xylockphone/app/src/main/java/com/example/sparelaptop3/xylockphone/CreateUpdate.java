package com.example.sparelaptop3.xylockphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import org.billthefarmer.mididriver.MidiDriver;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

public class CreateUpdate extends AppCompatActivity implements View.OnTouchListener{

    private MidiSynthInterface midiSynth;
    private ImageButton buttonPlayNote;
    public static final String TAG = "YOUR-TAG-NAME";
    private Note A;
    private Note C;
    private Note D;
    private Note E;
    private Note F;
    private Note G;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update);

        ImageButton bA = (ImageButton) findViewById(R.id.XyA);
        ImageButton bC = (ImageButton) findViewById(R.id.XyC);
        ImageButton bD = (ImageButton) findViewById(R.id.XyD);
        ImageButton bE = (ImageButton) findViewById(R.id.XyE);
        ImageButton bF = (ImageButton) findViewById(R.id.XyF);
        ImageButton bG = (ImageButton) findViewById(R.id.XyG);

        View.OnClickListener declarePressedKey = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Find which note was played. The note is the third character
                char strNote = getResources().getResourceEntryName(v.getId()).charAt(2);
                Log.d(TAG, strNote + " was clicked");

                //Add to the notes played, the new note played
                TextView notesPlayed = (TextView) findViewById(R.id.notesPlayed);
                notesPlayed.setText(notesPlayed.getText() + ", " + strNote);
                switch (strNote) {
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
        buttonPlayNote.setOnTouchListener(this);

        midiSynth = new MidiSynthInterface();
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

    @Override
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
    }
}
