package com.example.harkka10;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {
    WebView web;
    String Url;
    SearchView searchView;
    ArrayList<String> Pages = new ArrayList<String>();
    ListIterator<String> iterator;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        web = (WebView) findViewById(R.id.web);
        searchView = (SearchView) findViewById(R.id.action_search);
        web.setWebViewClient(new WebViewClient());
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        iterator = Pages.listIterator();

        setSupportActionBar(toolbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals("index.html")) {
                    Url = "index.html";
                    web.loadUrl("file:///android_asset/index.html");
                } else {
                    Url = "http://" + query;
                    if (iterator.hasNext()) {
                        iterator.next();
                    }
                    while (iterator.hasNext()) {
                        iterator.next();
                        iterator.remove();
                    }
                    if(Pages.size()>9) {
                        Pages.remove(0);
                        iterator = Pages.listIterator(9);
                    }
                    iterator.add(Url);
                    web.loadUrl(Url);
                    System.out.println(Pages);
                    searchView.clearFocus();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void refresh(View v) {
        if (Url.equals("index.html")) {
            web.evaluateJavascript("javascript:shoutOut()", null);
        } else {
            Url = web.getUrl();
            web.loadUrl(Url);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void backwards(View v) {
        if (Url.equals("index.html")) {
            web.evaluateJavascript("javascript:initialize()", null);
        }
        if (iterator.hasPrevious()) {
            if (!iterator.hasNext()) {
                iterator.previous();
            }
            Url = iterator.previous();
            web.loadUrl(Url);
            Toast.makeText(this, "Taakse", Toast.LENGTH_SHORT).show();
        }
    }

    public void forwards(View v) {
        if (Url.equals("index.html")) {
        }
        if (iterator.hasNext()) {
            if (!iterator.hasPrevious()) {
                iterator.next();
            }
            Url = iterator.next();
            web.loadUrl(Url);
            Toast.makeText(this, "Eteen", Toast.LENGTH_SHORT).show();
        }
    }
    public void clearUrls() {

    }
}
