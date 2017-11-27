package co.appreactor.notificacionespull.negocio.actividades;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import co.appreactor.notificacionespull.R;

public class MainActivity extends AppCompatActivity {

    private Button btnLanzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnLanzar = (Button) findViewById(R.id.btnLanzar);

        btnLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startService(
                        new Intent(
                                MainActivity.this,
                                ServicioAlarma.class
                        )
                );*/
                mostrarNotificacion();
            }
        });

        //AlarmaUtil.crearAlarma(MainActivity.this,21);
    }

    private void mostrarNotificacion() {
        // objeto que genera una instancia de notificacion pull
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(MainActivity.this);
        // aplicar un icono para la notificacion
        notificacion.setSmallIcon(R.mipmap.ic_launcher_round);
        // Asignar titulo a la notificacion
        notificacion.setContentTitle("Notificación pull de prueba");
        // Asignar el texto de la notificacion
        notificacion.setContentText("Hola, soy una notificación de prueba");
        // definir prioridad a la notificacion
        notificacion.setPriority(NotificationCompat.PRIORITY_MAX);
        // aplicar vibracion a la notificacion
        notificacion.setDefaults(NotificationCompat.DEFAULT_ALL);

        // Asignar un sonido de tono a la notificacion
        Uri sonidoDefecto = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificacion.setSound(sonidoDefecto);
        notificacion.setAutoCancel(false);

        // intent para redireccionar a la aplicacion al momento de dar click
        PendingIntent intencionPendiente = PendingIntent.getActivity(
                MainActivity.this,
                99,
                new Intent(MainActivity.this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        notificacion.setContentIntent(intencionPendiente);

        // Invocar al administrador de notificaciones y lanzar la notificacion
        NotificationManager manejador = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manejador.notify(123,notificacion.build());
    }
}
