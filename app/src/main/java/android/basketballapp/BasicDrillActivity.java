package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class BasicDrillActivity extends AppCompatActivity {

    protected final static String WEB_ASSETS_PATH = "file:///android_asset/web/";
    protected String drillName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        Intent parentIntent = getIntent();
        drillName = parentIntent.getStringExtra("drillName");
        this.setTitle(drillName);

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(WEB_ASSETS_PATH + parentIntent.getStringExtra("htmlFile"));
    }

    protected int getLayoutResourceId() {
        return R.layout.activity_basic_drill;
    }
}
