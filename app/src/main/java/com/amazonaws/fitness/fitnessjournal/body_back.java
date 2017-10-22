package com.amazonaws.fitness.fitnessjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.fitness.R;

public class body_back extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_back);

        TextView t = (TextView) findViewById(R.id.turnFront);
        t.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(body_back.this, Body.class);
                startActivity(intent);
            }
        });

    }


}
