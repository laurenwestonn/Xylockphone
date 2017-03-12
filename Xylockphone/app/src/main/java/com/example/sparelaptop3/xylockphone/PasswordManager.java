package com.example.sparelaptop3.xylockphone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public class PasswordManager implements Serializable {

    private static AppCompatActivity app;

    public PasswordManager(AppCompatActivity app){
        this.app = app;
        loadFromFile(app);
    } //do not instantite
    private static HashMap<String, Password> passwords = new HashMap<String, Password>();
    private static AssetManager am;

    public static Password getPassword(String appName){
        return passwords.get(appName);
    }

    static void setPassword(String appName, Password password){
        passwords.put(appName, password);
        saveToFile(app);
    }

    static void removePassword(String appName){
        passwords.remove(appName);
        saveToFile(app);
    }

    public static HashMap<String, Password> getAll() {
        return passwords;
    }
    static void loadFromFile(AppCompatActivity app){
        am = app.getApplicationContext().getAssets();
        try {
            //     InputStream fi = am.open("passwords.txt");
            File input = new File(app.getApplicationContext().getFilesDir() + "/passwords.txt");
            if (!input.exists()){
                input.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(input));

            String line = "";
            passwords.clear();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(":");
                String appName = values[0];
                CharSequence chars = values[1];
                String instrument = values[2];
                Instrument i;
                switch (instrument) {
                    case "Xylophone":
                        i = new Xylophone();
                        break;
                    // Other instruments would be added here
                    default :
                        i = new Xylophone();
                        break;
                }
                Password p = new Password(i, chars);
                passwords.put(appName, p);
            }
            passwords.size();
        } catch (IOException i) {
            File toDelete = new File(app.getApplicationContext().getFilesDir() + "/passwords.txt");
            toDelete.delete();
        }

    }

    static void saveToFile(AppCompatActivity app){
        try {
            FileOutputStream fo = app.getApplicationContext().openFileOutput("passwords.txt", Context.MODE_APPEND);
            PrintWriter pw = new PrintWriter(app.getApplicationContext().getFilesDir() + "/passwords.txt");
            for (HashMap.Entry<String, Password> password : passwords.entrySet()) {
                String appName = password.getKey();
                Password appPass = password.getValue();
                pw.println(appName + ":" + appPass.getNotes() + ":" + appPass.getInstrument().getName());
            }
            pw.flush();
            File file = new File(app.getApplicationContext().getFilesDir() + "/passwords.txt");



        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}