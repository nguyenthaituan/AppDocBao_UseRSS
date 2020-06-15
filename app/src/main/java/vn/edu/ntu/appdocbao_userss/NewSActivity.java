package vn.edu.ntu.appdocbao_userss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewSActivity extends AppCompatActivity {

    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_s);

        webView = findViewById(R.id.webviewTinTuc);

        Intent intent = getIntent();
        String link = intent.getStringExtra("linkTinTuc");
        //Toast.makeText(this, link, Toast.LENGTH_SHORT).show();
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}
