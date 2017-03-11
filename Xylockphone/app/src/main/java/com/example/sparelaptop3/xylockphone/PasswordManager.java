package com.example.sparelaptop3.xylockphone;

import java.lang.reflect.Array;
import java.util.*;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public class PasswordManager {
    private PasswordManager(){} //do not instantite
    private static HashMap passwords;
    private static AssetManager am;

    private static Password getPassword(String appName){
        return null;
    }

    static void setPassword(String appName){
        //something something from the list
    }

    static void deserialise(AppCompatActivity app){
        //something something passwords = something;
        am = app.getApplicationContext().getAssets();

    }

    static void serialise(AppCompatActivity app){
        //something something passwords = something;
        am = app.getApplicationContext().getAssets();

    }
}
