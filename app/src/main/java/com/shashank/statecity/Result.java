package com.shashank.statecity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView stateText,districtsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        String districts = intent.getStringExtra("districts");
        stateText=(TextView)findViewById(R.id.state);
        districtsText=(TextView)findViewById(R.id.districts);
        stateText.setText(state);
        districtsText.setText(districts);
    }
}
