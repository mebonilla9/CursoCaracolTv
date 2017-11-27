package co.appreactor.pokedex.persistencia.servicios;

import co.appreactor.pokedex.persistencia.entidades.PokemonRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lord_nightmare on 20/11/17.
 */

public interface PokemonCall {

    // directivas de acceso a los web services rest del PokeAPI
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

}
