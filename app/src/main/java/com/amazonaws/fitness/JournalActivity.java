package com.amazonaws.fitness;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.amazonaws.fitness.fitnessjournal.Body;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.amazonaws.fitness.R.id.btnJournalFilter;

public class JournalActivity extends AppCompatActivity {
    public static String urlConnection = null;
    private NavigationView nDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;
    private ListView attributesList;

    TableLayout tableLayout;
    TableRow tableRow;
    TextView tvDateWorkOut, tvNoOfWork, tvBodyPart, tvExercise;

    // Cognito user objects
    private CognitoUser user;
    private CognitoUserSession session;
    private CognitoUserDetails details;

    // User details
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        tableLayout = (TableLayout) findViewById(R.id.maintable);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Set toolbar for this screen
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        TextView main_title = (TextView) findViewById(R.id.main_toolbar_title);
        main_title.setText("JournalActivity");
        setSupportActionBar(toolbar);

        // Set navigation drawer for this screen
        mDrawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        nDrawer = (NavigationView) findViewById(R.id.nav_view);
        setNavDrawer();
        init();
        View navigationHeader = nDrawer.getHeaderView(0);
        TextView navHeaderSubTitle = (TextView) navigationHeader.findViewById(R.id.textViewNavUserSub);
        navHeaderSubTitle.setText(username);

        final EditText edtJournalFilter = (EditText)findViewById(R.id.edtJournalFilter);


//        String urlToServer = "https://b2kq977qb3.execute-api.us-west-2.amazonaws.com/prod/journal?email="+AppHelper.getCurrUser();
//        new JournalActivity.JSONTask().execute(urlToServer);
//        while (urlConnection == null){
//        }
//        AddData();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/octet-stream");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"sds\", \"dateworkout\": \"awdw\", \"noofwork\": 2, \"bodypart\": \"chest\", \"exercise\": \"sfef\"\n}");
                Request request = new Request.Builder()
                        .url("https://b2kq977qb3.execute-api.us-west-2.amazonaws.com/prod/journal?email=khoi1")
                        .get()
                        .addHeader("authorization", "eyJraWQiOiJ0TmxcLzJneDJNVlNQbmNFTUxFVGNCNVpIaXlIcEI0c0dUcmViQjdmVUE0UT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlMDJiZDllMC00YzZlLTRhNzUtOWNjNi0zYjllNGE4ZWRhMTQiLCJhdWQiOiI2bmJtbTc3YzBpaDE2bnNxODQ4ZXBxMmRhYyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJldmVudF9pZCI6IjQwNzA1Y2E2LWQ4YzItMTFlNy1iNjRhLTc5MWMyYTVkZjdmNCIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTEyMzcxNDQ3LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8wSXAyeEFERUUiLCJjb2duaXRvOnVzZXJuYW1lIjoia2hvaTEiLCJleHAiOjE1MTIzNzUwNDcsImlhdCI6MTUxMjM3MTQ0NywiZW1haWwiOiJraG9pMDV0MkBnbWFpbC5jb20ifQ.Rzl3Eiu4bH5ujppqZX86Hi3rFMRqPTaRQd6JfCeJtvAqf78XWyvneI6SnG2z2EPLja5owIO0pBZgYaLBZS5zU_a0bnkO64dgIaRvoGRDtOv8R7iC_fO1oNwkmFm1cKTli0lwMVb77rSb3H3-RVmoc-KAE2HHZSWtGIozgGVU-yzJyaSKTlK0PYjGhnr5a4zuYOrIezdgzMwT7Jig-oswvvtt3fElP-Y_QZuYXrhYOGGAAAWHdqp-AljFAR6xNWVyHfXhXcNaSfkWKRa0v9SQDmoZtcSylRumGLemz3hP-a_qwEI0iioFEgmXawWfHSfOeLUBme_MPYnrBwnoXo_PCQ")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "023bec72-5f16-a1d1-2d51-76a1ea431dff")
                        .build();
                try {
                    com.squareup.okhttp.Response response = client.newCall(request).execute();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.body().byteStream()));

                    StringBuffer result = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }

                    JSONObject o = new JSONObject(result.toString());
                    urlConnection = o.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
        while (urlConnection == null){
        }
        AddData();

        Button btnRefreshFilter = (Button) findViewById(R.id.btnRefreshJournal);
        btnRefreshFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = tableLayout.getChildCount();
                for (int i = 1; i < count; i++) {
                    View child = tableLayout.getChildAt(i);
                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                }

                AddData();
            }
        });

        Button btnJournalFilter = (Button) findViewById(R.id.btnJournalFilter);
        btnJournalFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = tableLayout.getChildCount();
                for (int i = 1; i < count; i++) {
                    View child = tableLayout.getChildAt(i);
                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                }

                try {
                    final JSONObject jsonObject = new JSONObject(urlConnection);
                    final JSONArray jsonArrayJournal = jsonObject.getJSONArray("journalList");
                    for (int i = 0; i < jsonArrayJournal.length(); i++) {
                        try {
                            String filterContent = edtJournalFilter.getText().toString().trim();
                            if (jsonArrayJournal.getJSONObject(i).getString("bodypart").equals(filterContent)) {
                                //System.out.println("bodypart:" + jsonArrayJournal.getJSONObject(i).getString("bodypart"));
                                JSONObject objJsonJournal = jsonArrayJournal.getJSONObject(i);
                                //Toast.makeText(JournalActivity.this, "" +i +edtJournalFilter.getText(),Toast.LENGTH_LONG).show();
                                tableRow = new TableRow(JournalActivity.this);
                                tableRow.setLayoutParams(new LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));

                                tvDateWorkOut = new TextView(JournalActivity.this);
                                String dateworkout = objJsonJournal.getString("dateworkout");
                                tvDateWorkOut.setText(dateworkout.substring(0, dateworkout.indexOf(' ')));
                                tvDateWorkOut.setTextSize(10);
                                tableRow.addView(tvDateWorkOut);

                                tvNoOfWork = new TextView(JournalActivity.this);
                                tvNoOfWork.setText(objJsonJournal.getString("noofwork"));
                                tvNoOfWork.setTextSize(10);
                                tableRow.addView(tvNoOfWork);

                                tvExercise = new TextView(JournalActivity.this);
                                tvExercise.setText(objJsonJournal.getString("exercise"));
                                tvExercise.setTextSize(10);
                                tableRow.addView(tvExercise);

                                tvBodyPart = new TextView(JournalActivity.this);
                                tvBodyPart.setText(objJsonJournal.getString("bodypart"));
                                tvBodyPart.setTextSize(10);
                                tableRow.addView(tvBodyPart);

                                tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
        });
    }

    public void AddData(){
        try{
        final JSONObject jsonObject = new JSONObject(urlConnection);
        final JSONArray jsonArrayJournal = jsonObject.getJSONArray("journalList");
        for(int i=0; i< jsonArrayJournal.length(); i++){
            JSONObject objJsonJournal = jsonArrayJournal.getJSONObject(i);

            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

            tvDateWorkOut = new TextView(this);
            String dateworkout = objJsonJournal.getString("dateworkout");
            tvDateWorkOut.setText(dateworkout.substring(0, dateworkout.indexOf(' ')));
            tvDateWorkOut.setTextSize(10);
            tableRow.addView(tvDateWorkOut);

            tvNoOfWork = new TextView(this);
            tvNoOfWork.setText(objJsonJournal.getString("noofwork"));
            tvNoOfWork.setTextSize(10);
            tableRow.addView(tvNoOfWork);

            tvExercise = new TextView(this);
            tvExercise.setText(objJsonJournal.getString("exercise"));
            tvExercise.setTextSize(10);
            tableRow.addView(tvExercise);

            tvBodyPart = new TextView(this);
            tvBodyPart.setText(objJsonJournal.getString("bodypart"));
            tvBodyPart.setTextSize(10);
            tableRow.addView(tvBodyPart);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        }

        } catch (JSONException e) {
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
    // Handle when the a navigation item is selected
    private void setNavDrawer() {
        nDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                performAction(item);
                return true;
            }
        });
    }

    // Perform the action for the selected navigation item
    private void performAction(MenuItem item) {
        // Close the navigation drawer
        mDrawer.closeDrawers();

        // Find which item was selected
        switch(item.getItemId()) {
//            case R.id.nav_user_add_attribute:
//                // Add a new attribute
//                addAttribute();
//                break;

            case R.id.nav_user_change_password:
                // Change password
                changePassword();
                break;
//            case R.id.nav_user_verify_attribute:
//                // Confirm new user
//                // confirmUser();
//                attributesVerification();
//                break;
//            case R.id.nav_user_settings:
//                // Show user settings
//                showSettings();
//                break;
            case R.id.nav_user_sign_out:
                // Sign out from this account
                signOut();
                break;
        }
    }

    // Change user password
    private void changePassword() {
        Intent changePssActivity = new Intent(this, ChangePasswordActivity.class);
        startActivity(changePssActivity);
    }

    // Verify attributes
    private void attributesVerification() {
        Intent attrbutesActivity = new Intent(this,VerifyActivity.class);
        startActivityForResult(attrbutesActivity, 21);
    }

    // Sign out user
    private void signOut() {
        user.signOut();
        exit();
    }

    // Initialize this activity
    private void init() {
        // Get the user name
        Bundle extras = getIntent().getExtras();
        username = AppHelper.getCurrUser();
        user = AppHelper.getPool().getUser(username);
        getDetails();
    }

    // Get user details from CIP service
    private void getDetails() {
        AppHelper.getPool().getUser(username).getDetailsInBackground(detailsHandler);
    }

    GetDetailsHandler detailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            closeWaitDialog();
            // Store details in the AppHandler
            AppHelper.setUserDetails(cognitoUserDetails);
            showAttributes();
            // Trusted devices?
            handleTrustedDevice();
        }

        @Override
        public void onFailure(Exception exception) {
            // closeWaitDialog();
            //showDialogMessage("Could not fetch user details!", AppHelper.formatException(exception), true);
        }
    };

    private void handleTrustedDevice() {
        CognitoDevice newDevice = AppHelper.getNewDevice();
        if (newDevice != null) {
            AppHelper.newDevice(null);
            //trustedDeviceDialog(newDevice);
        }
    }

    private void showAttributes() {
        final UserAttributesAdapter attributesAdapter = new UserAttributesAdapter(getApplicationContext());
        final ListView attributesListView;
//        //attributesListView = (ListView) findViewById(R.id.listViewUserAttributes);
//        attributesListView.setAdapter(attributesAdapter);
//        attributesList = attributesListView;
//
//        attributesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView data = (TextView) view.findViewById(R.id.editTextUserDetailInput);
//                String attributeType = data.getHint().toString();
//                String attributeValue = data.getText().toString();
//                showUserDetail(attributeType, attributeValue);
//            }
//        });
    }

    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        exit();
                    }
                } catch (Exception e) {
                    // Log failure
                    Log.e("JournalActivity","Dialog dismiss failed");
                    if(exit) {
                        exit();
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }

    private void exit () {
        Intent intent = new Intent();
        if(username == null)
            username = "";
        intent.putExtra("name",username);
        setResult(RESULT_OK, intent);
        finish();
    }

}
