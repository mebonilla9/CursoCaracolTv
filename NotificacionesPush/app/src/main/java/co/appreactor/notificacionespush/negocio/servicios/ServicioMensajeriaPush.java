package co.appreactor.notificacionespush.negocio.servicios;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import co.appreactor.notificacionespush.R;
import co.appreactor.notificacionespush.negocio.util.NotificacionUtil;

public class ServicioMensajeriaPush extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // validar si los datos del mensaje remoto tienen tamaño
        if (remoteMessage.getData().size() <= 0){
            return;
        }

        // validar que el tema o topico del mensaje recibido sera al que
        // estamos suscritos

        if (remoteMessage.getFrom().equals("/topics/"+getString(R.string.tema_notificacion))){

            String titulo = remoteMessage.getNotification().getTitle();
            String mensaje = remoteMessage.getNotification().getBody();

            // Invocacion de la notificacion
            NotificacionUtil.mostrarNotificacion(this, titulo, mensaje);
        }



    }
}
