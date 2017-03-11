package com.example.sparelaptop3.xylockphone;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public class PasswordManager {
    private PasswordManager(){} //do not instantite
    private static HashMap<String, Password> passwords = new HashMap<String, Password>();
    private static AssetManager am;

    private static Password getPassword(String appName){
        return passwords.get(appName);
    }

    static void setPassword(String appName, Password password){
        passwords.put(appName, password);
    }

    static void deserialise(AppCompatActivity app){
        am = app.getApplicationContext().getAssets();
        try {
            InputStream fi = am.open("passwords.txt");
            ObjectInputStream is = new ObjectInputStream(fi);
            passwords = (HashMap<String, Password>) is.readObject();
        } catch (IOException|ClassNotFoundException i) {
            i.printStackTrace();
        }

    }

    static void serialise(AppCompatActivity app){
        try {
            FileOutputStream fo = app.getApplicationContext().openFileOutput("passwords.txt", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(passwords);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}