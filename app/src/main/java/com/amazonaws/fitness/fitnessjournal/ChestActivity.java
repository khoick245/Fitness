package com.amazonaws.fitness.fitnessjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.fitness.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChestActivity extends Activity {
    public static String urlConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest_activity);

        ListView lv = (ListView) findViewById(R.id.lv);
        final TextView tv = (TextView) findViewById(R.id.tv);
//        String[] chest_exercises = new String[] {
//                "Barbell Bench Press",
//                "Flat Bench Dumbbell Press",
//                "Incline Dumbbell Press",
//                "Dips For Chest ",
//                "Incline Bench Cable Fly",
//                "Incline Dumbbell Pull-Over"
//        };

        try {

           new JSONTask().execute("https://7mbivmda6c.execute-api.us-west-2.amazonaws.com/prod/bodypartresource?partname=Chest");
            TimeUnit.SECONDS.sleep(2);
            JSONObject jsonObject = new JSONObject(urlConnection);
            JSONArray arr = jsonObject.getJSONArray("bodyPartInfors");
            ArrayList<String> chest_exercises = new ArrayList<String>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                chest_exercises.add(o.getString("exercisename"));
                String[] stringArr = new String[chest_exercises.size()];
                stringArr = chest_exercises.toArray(stringArr);

                for(String s : stringArr)
                    System.out.println(s);

                List<String> exercise_list = new ArrayList<String>(Arrays.asList(stringArr));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, exercise_list);

                lv.setAdapter(arrayAdapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(position==0){
                            Intent i=new Intent(ChestActivity.this,benchpress.class);
                            startActivity(i);
                        }
                        else if(position ==1)
                        {
                            Intent i=new Intent(ChestActivity.this,dumbbellpress.class);
                            startActivity(i);
                        }
                    }
                });

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class JSONTask extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                urlConnection = buffer.toString();
                Body.urlString = buffer.toString();
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            urlConnection = result;
            Body.urlString = result;
            Log.d("working",result);
        }

    }
}