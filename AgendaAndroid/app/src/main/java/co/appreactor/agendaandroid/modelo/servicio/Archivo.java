package co.appreactor.agendaandroid.modelo.servicio;

import android.os.Environment;

import java.util.List;

import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 2/11/17.
 */

public interface Archivo {

    String RUTA_ARCHIVO = Environment.getExternalStorageDirectory().getAbsolutePath()+"/agenda";

    void escribir(List<Persona> listaPersonas);

    List<Persona> leer();

}
