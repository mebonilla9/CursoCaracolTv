package co.appreactor.noticias.negocio.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import co.appreactor.noticias.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tareaTemporal();
    }

    private void tareaTemporal() {
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        };
        Timer intervalo = new Timer();
        intervalo.schedule(tarea, 1500);
    }
}
