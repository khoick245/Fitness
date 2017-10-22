package com.amazonaws.fitness.fitnessjournal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.amazonaws.fitness.R;

public class dumbbellpress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchpress);

        String url =
                "https://www.youtube.com/embed/Vc63DPUoA40";
        WebView view =(WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }
}
