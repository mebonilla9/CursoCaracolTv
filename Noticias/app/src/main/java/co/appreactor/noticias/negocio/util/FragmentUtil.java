package co.appreactor.noticias.negocio.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import co.appreactor.noticias.R;
import co.appreactor.noticias.negocio.fragmentos.ListarCategoriaFragment;
import co.appreactor.noticias.negocio.fragmentos.ListarNoticiaFragment;
import co.appreactor.noticias.negocio.fragmentos.NuevoCategoriaFragment;
import co.appreactor.noticias.negocio.fragmentos.NuevoNoticiaFragment;

/**
 * Created by lord_nightmare on 9/10/17.
 */

public final class FragmentUtil {

    public static void obtenerFragmento(int idVista, Bundle argumentos,
                                        FragmentTransaction transaccion,
                                        int contenedor){

        Fragment fragmento = null;
        String tag = "";
        // asignar el tipo de fragmento a utilizar segun el item de menu
        // seleccionado.
        switch (idVista){
            case R.id.miNuevaNoticia:
                fragmento = new NuevoNoticiaFragment();
                tag = "Registrar Evento";
                break;
            case R.id.miNuevaCategoria:
                fragmento = new NuevoCategoriaFragment();
                tag = "Consultar Salas";
                break;
            case R.id.miListaCategoria:
                fragmento = new ListarCategoriaFragment();
                break;
            case R.id.miListaNoticia:
                fragmento = new ListarNoticiaFragment();
                break;
            default:
                fragmento = new Fragment();
                tag = "Fragment";
                break;
        }
        // Determinar si hay argumentos para enviar al fragmento
        if (argumentos != null){
            fragmento.setArguments(argumentos);
        }
        // reemplazar el fragmento en el contenedor
        transaccion.replace(contenedor,fragmento).addToBackStack(tag).commit();
    }
}
