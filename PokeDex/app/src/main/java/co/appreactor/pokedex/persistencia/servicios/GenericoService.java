package co.appreactor.pokedex.persistencia.servicios;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lord_nightmare on 20/11/17.
 */

public class GenericoService {

    protected final Retrofit servicio;
    protected final Context contexto;

    public GenericoService(Context contexto){

        // Asignar el context recibido
        this.contexto = contexto;

        // Asignar valor y parametros de configuracion al objeto de
        // Retrofit
        servicio = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // timeouts
        // Interceptores -> Network
        // DateConverter -> MSSQL

    }

}
