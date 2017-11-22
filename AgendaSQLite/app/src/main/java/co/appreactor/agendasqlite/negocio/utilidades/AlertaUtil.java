package co.appreactor.agendasqlite.negocio.utilidades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by lord_nightmare on 2/11/17.
 */

public final class AlertaUtil {

    public static void mostrarAlerta(String titulo, String mensaje,
                                     DialogInterface.OnClickListener aceptar,
                                     DialogInterface.OnClickListener cancelar,
                                     Context contexto) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);
        alerta.setPositiveButton("Aceptar",aceptar);
        alerta.setNegativeButton("Cancelar",cancelar);
        alerta.show();
    }
}
