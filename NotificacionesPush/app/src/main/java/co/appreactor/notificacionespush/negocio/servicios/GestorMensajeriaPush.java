package co.appreactor.notificacionespush.negocio.servicios;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import co.appreactor.notificacionespush.R;

/**
 * Created by lord_nightmare on 22/11/17.
 */

public class GestorMensajeriaPush extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // obtener valor del token utilizando la instancia de firebase
        String token = FirebaseInstanceId.getInstance().getToken();

        // suscribir al token de usuario a un tema de notificacion
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.tema_notificacion));
    }
}
