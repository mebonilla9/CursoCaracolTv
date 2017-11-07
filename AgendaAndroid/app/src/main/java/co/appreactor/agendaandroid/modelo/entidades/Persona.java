package co.appreactor.agendaandroid.modelo.entidades;

import java.io.Serializable;

/**
 * Created by lord_nightmare on 1/11/17.
 */

public class Persona implements Serializable {

    private String nombre;
    private String correo;
    private String telefono;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
