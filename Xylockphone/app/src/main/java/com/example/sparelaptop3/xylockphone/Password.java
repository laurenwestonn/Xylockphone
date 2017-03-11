package com.example.sparelaptop3.xylockphone;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public class Password {
    private CharSequence chars;
    private Instrument instrument;

    public Password(Instrument instrument, CharSequence chars){
        this.instrument = instrument;
        this.chars = chars;
    }

    public Instrument getInstrument(){
        return instrument;
    }

    public CharSequence getNotes() { return chars; }

   public void SetNotes(CharSequence chars)
    {
        this.chars = chars;
    }

    public boolean ComparePassword(CharSequence charsIn)
    {
        if (chars == null) {
            return false;
        }
        return chars.equals(charsIn);
    }
}
