package co.appreactor.agendasqlite.negocio.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lord_nightmare on 7/11/17.
 */

public final class PreferenciasUtil {

    public static void guardarPreferencia(String llave, String valor, Context contexto) {
        SharedPreferences preferencias = contexto.getSharedPreferences("agenda", Context.MODE_PRIVATE);
        // Obtener el editor de preferencias para guardar informacion
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(llave, valor);
        editor.apply();
    }

    public static String leerPreferencias(String llave, Context contexto) {
        SharedPreferences preferences = contexto.getSharedPreferences("agenda", Context.MODE_PRIVATE);
        return preferences.getString(llave, "");
    }

}
