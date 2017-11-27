package co.appreactor.notificacionespush.negocio.aplicacion;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;

import co.appreactor.notificacionespush.negocio.servicios.GestorMensajeriaPush;

/**
 * Created by lord_nightmare on 22/11/17.
 */

public class NotificacionesPushApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Validar que el servicio que lee la notificaciones push este en ejecucion
        if (!servicioEnEjecucion(GestorMensajeriaPush.class.getName()))
            startService(new Intent(this,GestorMensajeriaPush.class));
    }

    @SuppressWarnings("deprecated")
    private boolean servicioEnEjecucion(String servicio){
        // Obtener al administrador de actividades
        ActivityManager manejador =
                (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo ejecutando :
                manejador.getRunningServices(Integer.MAX_VALUE)){
            if (ejecutando.service.getClassName().equals(servicio))
                return true;
        }

        return false;
    }
}
