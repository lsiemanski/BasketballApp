package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class DrillActivity extends AppCompatActivity {

    private final static String WEB_ASSETS_PATH = "file:///android_asset/web/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill);
        Intent parentIntent = getIntent();
        this.setTitle(parentIntent.getStringExtra("drillName"));
        WebView webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(WEB_ASSETS_PATH + parentIntent.getStringExtra("htmlFile"));
    }
}
