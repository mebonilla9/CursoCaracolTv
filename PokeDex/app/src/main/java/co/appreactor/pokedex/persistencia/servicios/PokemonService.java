package co.appreactor.pokedex.persistencia.servicios;

import android.content.Context;

import co.appreactor.pokedex.negocio.actividades.MainActivity;
import co.appreactor.pokedex.persistencia.entidades.PokemonRespuesta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lord_nightmare on 20/11/17.
 */

public class PokemonService extends GenericoService {

    private PokemonCall call;

    public PokemonService(Context contexto) {
        super(contexto);
        // asignarle valor al atributo servicio
        call = this.servicio.create(PokemonCall.class);
    }

    public void consultarListaPokemon(int limit, int offset){
        // Crear objeto que representa la peticion al
        // servidor a traves del web services REST
        Call<PokemonRespuesta> respuesta = this.call.obtenerListaPokemon
                (limit, offset);
        // conversion a la peticion en modo asincrono
        respuesta.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                // Valide que la respuesta no sea satisfactoria
                if (!response.isSuccessful()){
                    return;
                }

                // Delegar a un metodo de la actividad el procesamiento de la
                // informacion recibida del web service

                ((MainActivity) contexto).procesarRespuesta(response);
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {

            }
        });
    }
}
