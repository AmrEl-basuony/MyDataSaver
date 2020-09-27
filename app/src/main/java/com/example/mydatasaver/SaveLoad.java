package com.example.mydatasaver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SaveLoad extends AppCompatActivity {
    
    public static final String PREF_NAME="MyPrefFile";
    public static final String MYTEXT="text";
    public static final String MYPHONE="phone";
    static final int READ_BLOCK_SIZE = 100;
    public String text,phone;
    SharedPreferences sharedpreferences;
    DBManager mgr;
    TextView sqliteTextView,sqlitePhoneView;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveload);
        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Intent intent = getIntent();

        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String num = intent.getStringExtra(MainActivity.EXTRA_NUM);
        text=message;
        phone=num;

        TextView prefTextView = findViewById(R.id.prefText);
        prefTextView.setText(message);
        TextView prefPhoneView = findViewById(R.id.prefPhone);
        prefPhoneView.setText(num);

        TextView internalTextView = findViewById(R.id.internalText);
        internalTextView.setText(message);
        TextView internalPhoneView = findViewById(R.id.internalPhone);
        internalPhoneView.setText(num);

        mgr =new DBManager(this);
        sqliteTextView = findViewById(R.id.sqliteText);
        sqliteTextView.setText(message);
        sqlitePhoneView = findViewById(R.id.sqlitePhone);
        sqlitePhoneView.setText(num);
    }

    public void savePref(View view){
        SharedPreferences.Editor myEdit = sharedpreferences.edit();
        myEdit.putString(MYTEXT,text);
        myEdit.putString(MYPHONE,phone);
        myEdit.commit();
    }
    public void loadPref(View view){
        TextView prefTextView = findViewById(R.id.prefText);
        TextView prefPhoneView = findViewById(R.id.prefPhone);
        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYTEXT)) {
            prefTextView.setText(sharedpreferences.getString(MYTEXT, ""));
        }
        if (sharedpreferences.contains(MYPHONE)) {
            prefPhoneView.setText(sharedpreferences.getString(MYPHONE, ""));

        }
    }

    public void saveInternal(View v) {
        try {
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(text);
            outputWriter.close();


            fileout=openFileOutput("myphonefile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter2=new OutputStreamWriter(fileout);
            outputWriter2.write(phone);
            outputWriter2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadInternal(View v) {
        try {
            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String text="";
            String phone="";
            int charRead;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                text +=readstring;
            }
            TextView internalTextView = findViewById(R.id.internalText);
            internalTextView.setText(text);
            InputRead.close();

            fileIn=openFileInput("myphonefile.txt");
            InputStreamReader InputRead2= new InputStreamReader(fileIn);
            while ((charRead=InputRead2.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                phone +=readstring;
            }
            InputRead2.close();
            TextView internalPhoneView = findViewById(R.id.internalPhone);
            internalPhoneView.setText(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSqlite(View view){
       mgr.insert(text,phone);
    }
    public void loadSqlite(View view){
        mCursor = mgr.fetch();
        String name = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.NAME));
        String phone = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.PHONE));
        sqliteTextView.setText(name);
        sqlitePhoneView.setText(phone);
    }
}