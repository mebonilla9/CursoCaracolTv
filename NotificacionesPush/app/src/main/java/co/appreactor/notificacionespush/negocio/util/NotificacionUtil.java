package co.appreactor.notificacionespush.negocio.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import co.appreactor.notificacionespush.R;
import co.appreactor.notificacionespush.negocio.actividades.MainActivity;

/**
 * Created by lord_nightmare on 22/11/17.
 */

public final class NotificacionUtil {

    public static void mostrarNotificacion(Context contexto, String titulo, String mensaje){
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(contexto,"notificador");
        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setContentTitle(titulo);
        notificacion.setContentText(mensaje);
        notificacion.setPriority(NotificationCompat.PRIORITY_MAX);
        notificacion.setDefaults(NotificationCompat.DEFAULT_ALL);

        Uri rutaSonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificacion.setSound(rutaSonido);
        notificacion.setAutoCancel(true);

        PendingIntent intencion = PendingIntent.getActivity(
                contexto,
                789,
                new Intent(contexto, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        notificacion.setContentIntent(intencion);

        NotificationManager manejador =
                (NotificationManager) contexto.getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
        manejador.notify(789,notificacion.build());
    }
}
