package co.appreactor.agendaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import co.appreactor.agendaandroid.negocio.actividades.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        crearTareaTemporal();
    }

    private void crearTareaTemporal() {
        // Objeto que representa el proceso en funcion de tiempo
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        };
        // objeto que representa el intervalo de tiempo en el que se va a ejecutar la tarea temporal
        Timer intervalo = new Timer();
        intervalo.schedule(tarea,2000);
    }
}
