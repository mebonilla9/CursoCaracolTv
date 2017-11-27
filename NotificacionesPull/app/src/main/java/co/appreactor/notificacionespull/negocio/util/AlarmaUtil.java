package co.appreactor.notificacionespull.negocio.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import co.appreactor.notificacionespull.negocio.servicios.ServicioAlarma;

/**
 * Created by lord_nightmare on 21/11/17.
 */

public final class AlarmaUtil {

    public static void crearAlarma(Context contexto, int dia) {
        // Crear la intencion pendiente que va a diriginos al servicio
        // de notificacion
        PendingIntent intencionAlarma = PendingIntent.getActivity(
                contexto,
                456,
                new Intent(contexto, ServicioAlarma.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // obtener el tiempo de la alarma
        long tiempo = obtenerFecha(dia);

        // Invocar al administrador de alarmas para generar una "Alarma"
        // utilizando la intencion pendiente creada anteriormente
        AlarmManager manejador =
                (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);

        // Asignar la alarma con el tiempo especificado
        manejador.set(AlarmManager.RTC_WAKEUP, tiempo,intencionAlarma);
    }

    private static long obtenerFecha(int dia) {
        // obtener una instancia del calendario del sistema
        Calendar calendario = Calendar.getInstance();
        // asignacion de la fecha actual
        calendario.setTime(new Date());
        calendario.set(Calendar.DAY_OF_MONTH, dia);
        calendario.set(Calendar.HOUR_OF_DAY, 9);
        calendario.set(Calendar.MINUTE, 20);
        calendario.set(Calendar.SECOND, 0);
        return calendario.getTime().getTime();
    }
}
