package co.appreactor.pokedex.persistencia.entidades;

import java.util.ArrayList;

/**
 * Created by lord_nightmare on 20/11/17.
 */

public class PokemonRespuesta {

    private ArrayList<Pokemon> results;


    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
