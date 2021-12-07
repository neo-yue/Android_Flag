package com.example.flag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbactivity);


//        TextView title;
//        title=findViewById(R.id.title);

        Bundle extras = getIntent().getExtras();
    }
}