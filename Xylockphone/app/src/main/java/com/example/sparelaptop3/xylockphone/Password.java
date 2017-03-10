package com.example.sparelaptop3.xylockphone;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public class Password {
    private List<Note> notes;
    private Instrument instrument;

    public Password(Instrument i){
        instrument = i;
    }

    public Instrument getInstrument(){
        return instrument;
    }

}
