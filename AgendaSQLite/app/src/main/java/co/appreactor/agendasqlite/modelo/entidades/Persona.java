package co.appreactor.agendasqlite.modelo.entidades;

import java.io.Serializable;

/**
 * Created by lord_nightmare on 1/11/17.
 */

public class Persona implements Serializable {

    private int id;
    private String nombre;
    private String correo;
    private String telefono;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

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
