package com.amazonaws.fitness.fitnessjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.fitness.R;

import java.util.concurrent.TimeUnit;

public class Body extends AppCompatActivity {

   public static String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView t = (TextView) findViewById(R.id.turnBehind);
        t.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Body.this, body_back.class);
                startActivity(intent);
            }
        });

        Button sb = (Button) findViewById(R.id.shoulderButton);
        sb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChestActivity.JSONTask().execute("https://o3qfj6k4n5.execute-api.us-west-2.amazonaws.com/prod/test");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Body.this, urlString, Toast.LENGTH_SHORT).show();
            }
        });

        Button tt = (Button) findViewById(R.id.chestButton);
        tt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChestActivity.JSONTask().execute("https://7mbivmda6c.execute-api.us-west-2.amazonaws.com/prod/bodypartresource?partname=Chest");
                Intent intent = new Intent(Body.this, ChestActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_body, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
