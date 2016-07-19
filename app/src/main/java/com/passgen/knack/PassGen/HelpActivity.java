package com.passgen.knack.PassGen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpActivity extends AppCompatActivity
{
    String[] allItems = {"Информация о программе",
                         "Как пользоваться"};
    String[] subInfo = {"Описание"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
}
