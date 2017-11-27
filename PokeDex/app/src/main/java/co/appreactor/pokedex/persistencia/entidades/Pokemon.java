package co.appreactor.pokedex.persistencia.entidades;

import java.io.Serializable;

/**
 * Created by lord_nightmare on 20/11/17.
 */

public class Pokemon implements Serializable {

    private int number;
    private String name;
    private String url;


    public int getNumber() {
        // Crear un arreglo de partes para trabajar con metodo split
        String[] partes = this.url.split("/");
        return Integer.parseInt(partes[partes.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
