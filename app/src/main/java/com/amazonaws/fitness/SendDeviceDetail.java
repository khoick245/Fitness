package com.amazonaws.fitness;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

/**
 * Created by khoinguyen on 11/3/17.
 */

class SendDeviceDetails extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"weadaw1111\",\n  \"dateworkout\": \"awdw\",\n  \"noofwork\": 2,\n  \"bodypart\": \"chest\",\n  \"exercise\": \"sfef\"\n}");
//        Request request = new Request.Builder()
//                .url("https://b2kq977qb3.execute-api.us-west-2.amazonaws.com/prod/journal")
//                .post(body)
//                .addHeader("content-type", "application/json")
//                .addHeader("cache-control", "no-cache")
//                .addHeader("postman-token", "b135de99-a25f-cbb7-7426-c78cb87bf5f9")
//                .build();

//        OkHttpClient client = new OkHttpClient();
//
//                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//                RequestBody body = RequestBody.create(mediaType, "email=khoi123&dateworkout=khoi&noofwork=khoi&bodypart=chest&exercise=khoi");
//                Request request = new Request.Builder()
//                        .url("https://b2kq977qb3.execute-api.us-west-2.amazonaws.com/prod/journal")
//                        .post(body)
//                        .addHeader("content-type", "application/x-www-form-urlencoded")
//                        .addHeader("x-amz-date", "20171104T080543Z")
//                        .addHeader("authorization", "AWS4-HMAC-SHA256 Credential=AKIAIZFHCNMYTDBYF2EQ/20171104/us-west-2/execute-api/aws4_request, SignedHeaders=content-length;content-type;host;x-amz-date, Signature=f2d178f85a38329733e9df99d2667b2e600ad0f74275f817b78a7493c83eed46")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("postman-token", "b86e33ad-3584-9636-a1b8-293c6ff0b2ed")
//                        .build();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"sds\",\n  \"dateworkout\": \"awdw\",\n  \"noofwork\": 2,\n  \"bodypart\": \"chest\",\n  \"exercise\": \"sfef\"\n}");
        Request request = new Request.Builder()
                .url("https://b2kq977qb3.execute-api.us-west-2.amazonaws.com/prod/journal")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "b4defd15-e4df-1367-f7c1-9c433886a27e")
                .build();
        try {
            com.squareup.okhttp.Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("TAG", "OK"); // this is expecting a response code to be sent from your server upon receiving the POST data
    }
}
