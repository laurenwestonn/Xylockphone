package com.example.sparelaptop3.xylockphone;

/**
 * Created by JJ on 11/03/2017.
 */
import android.content.res.Resources;

import org.billthefarmer.mididriver.MidiDriver;
import android.util.Log;

public class MidiSynthInterface implements MidiDriver.OnMidiStartListener {

    private MidiDriver midiDriver;
    private byte[] event;

    public MidiSynthInterface () {
        // Instantiate the driver.
        midiDriver = new MidiDriver();
        // Set the listener.
        midiDriver.setOnMidiStartListener(this);
    }

    public void onMidiStart() {
        Log.d(this.getClass().getName(), "onMidiStart()");
    }

    public void setInstrument(int inst) {
        event = new byte[2];
        event[0] = (byte) (0xc0);  //code for a program change
        event[1] = (byte) inst; // the instrument we are changing to
        midiDriver.write(event);
    }

    public void playNote(Note note) {
        setInstrument(13);
        // Construct a note ON message for the middle C at maximum velocity on channel 1:
        event = new byte[3];
        event[0] = (byte) (0x90 | 0x00);  // 0x90 = note On, 0x00 = channel 1
        event[1] = (byte) note.getNote();  // 0x3C = middle C
        event[2] = (byte) 0x7F;  // 0x7F = the maximum velocity (127)

        // Internally this just calls write() and can be considered obsoleted:
        //midiDriver.queueEvent(event);

        // Send the MIDI event to the synthesizer.
        midiDriver.write(event);
    }

    public void stopNote(Note note) {

        // Construct a note OFF message for the middle C at minimum velocity on channel 1:
        event = new byte[3];
        event[0] = (byte) (0x80 | 0x00);  // 0x80 = note Off, 0x00 = channel 1
        event[1] = note.getNote();  // 0x3C = middle C
        event[2] = (byte) 0x00;  // 0x00 = the minimum velocity (0)

        // Send the MIDI event to the synthesizer.
        midiDriver.write(event);
    }

    public void stop() {
        midiDriver.stop();
    }

    public void start() {
        midiDriver.start();
    }
}
