package co.appreactor.mivistaweb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView wvNavegador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.wvNavegador = (WebView) findViewById(R.id.wvNavegador);
        wvNavegador.setWebViewClient(new WebViewClient());
        wvNavegador.loadUrl("https://android-arsenal.com");
        // permitir que el webview ejecute javascript
        wvNavegador.getSettings().setJavaScriptEnabled(true);
        // habilitar el control de zoom por gestos
        wvNavegador.getSettings().setBuiltInZoomControls(true);
        // inhabilitar los botones de zoom
        wvNavegador.getSettings().setDisplayZoomControls(true);
    }
}
