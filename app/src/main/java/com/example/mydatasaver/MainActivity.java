package com.example.mydatasaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.mydatasaver.MESSAGE";
    public static final String EXTRA_NUM = "com.example.mydatasaver.NUM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sendData(View view) {
        Intent intent = new Intent(MainActivity.this, SaveLoad.class);
        EditText inputText =findViewById(R.id.inputText);
        EditText inputPhone =findViewById(R.id.inputPhone);
        String message = inputText.getText().toString();
        String num = inputPhone.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_NUM, num);
        startActivity(intent);
    }


}