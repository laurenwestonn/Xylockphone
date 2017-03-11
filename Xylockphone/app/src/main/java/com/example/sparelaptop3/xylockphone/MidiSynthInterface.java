package com.example.sparelaptop3.xylockphone;

/**
 * Created by JJ on 11/03/2017.
 */
import android.content.res.Resources;

import org.billthefarmer.mididriver.MidiDriver;

import java.util.Locale;

public class MidiSynthInterface implements MidiDriver.OnMidiStartListener {

    private int Instrument;
    private MidiDriver midiDriver;


    public MidiSynthInterface () {
        midiDriver = new MidiDriver();
        if (midiDriver != null)
            midiDriver.setOnMidiStartListener(this);
        byte msg[] = new byte[2];
    }

    @Override
    public void onMidiStart()
    {

    }

    public void setInstrument(int instrument) {

    }

    public int getInstrument() {
        return 1;
    }


    public void playNote(int note) {

    }
}
