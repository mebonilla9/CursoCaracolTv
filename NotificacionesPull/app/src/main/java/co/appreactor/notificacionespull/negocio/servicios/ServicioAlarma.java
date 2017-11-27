package co.appreactor.notificacionespull.negocio.servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import co.appreactor.notificacionespull.R;
import co.appreactor.notificacionespull.negocio.actividades.MainActivity;

/**
 * Created by lord_nightmare on 21/11/17.
 */

public class ServicioAlarma extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mostrarNotificacion();
        return super.onStartCommand(intent, flags, startId);
    }

    private void mostrarNotificacion() {
        // objeto que genera una instancia de notificacion pull
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(ServicioAlarma.this);
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
                ServicioAlarma.this,
                99,
                new Intent(ServicioAlarma.this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        notificacion.setContentIntent(intencionPendiente);

        // Invocar al administrador de notificaciones y lanzar la notificacion
        NotificationManager manejador = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manejador.notify(123,notificacion.build());
    }
}
