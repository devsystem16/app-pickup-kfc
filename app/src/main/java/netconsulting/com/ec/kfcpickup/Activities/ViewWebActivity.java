package netconsulting.com.ec.kfcpickup.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import netconsulting.com.ec.kfcpickup.R;

public class ViewWebActivity extends AppCompatActivity {

    public static final String URL = "URL";
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_web);

        WebView webView = findViewById(R.id.webView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(URL)) {

            url = bundle.getString(URL);
            if (url.equals("N/A")) {
                Toast.makeText(getBaseContext(), R.string.noweb, Toast.LENGTH_LONG).show();
            } else {
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSupportMultipleWindows(true);
                webView.setWebViewClient(new CustomViewWebCliente());
                webView.loadUrl(url);

            }

        }
    }

    private class CustomViewWebCliente extends WebViewClient {
        ProgressBar progressBarWebView = findViewById(R.id.progressBarWebView);

        @Override
        public void onPageStarted(WebView view, String kyeUrl, Bitmap favicon) {
            progressBarWebView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String kyeUrl) {
            progressBarWebView.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        url = "";
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        url = "";
        super.onBackPressed();
    }
}