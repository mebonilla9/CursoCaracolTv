package co.appreactor.pokedex.negocio.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import co.appreactor.pokedex.R;
import co.appreactor.pokedex.negocio.adaptadores.Adaptador;
import co.appreactor.pokedex.persistencia.entidades.Pokemon;
import co.appreactor.pokedex.persistencia.entidades.PokemonRespuesta;
import co.appreactor.pokedex.persistencia.servicios.PokemonService;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvLista;

    private int offset;
    private final int limit = 20;

    private boolean aptoParaCargar;

    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.rvLista = (RecyclerView) findViewById(R.id.rvLista);
        adaptador = new Adaptador(MainActivity.this);
        rvLista.setHasFixedSize(true);

        // agregar administrador de layout
        rvLista.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

        // Importante, el adaptador es lo ultimo que deben asignarle al RecyclerView
        rvLista.setAdapter(adaptador);
        aptoParaCargar = true;

        asignarEventos();
        obtenerDatos(offset);
    }


    private void asignarEventos() {
        // Asignacion de listener para scroll de recyclerview
        rvLista.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    return;
                }
                if (!aptoParaCargar){
                    return;
                }
                // obtener la cantidad de items visibles dentro del recyclerView
                int itemsVisibles = rvLista.getLayoutManager().getChildCount();

                // obtener el total de items que posee el layout manager del
                // recyclerView
                int totalItems = rvLista.getLayoutManager().getItemCount();

                // obtener el numero de items desplazados hacia arriba en el
                // layout manager
                int itemsDesplazados =
                        ((GridLayoutManager)rvLista.getLayoutManager())
                                .findFirstVisibleItemPosition();

                if ((itemsVisibles + itemsDesplazados) >= totalItems){
                    aptoParaCargar = false;
                    offset += limit;
                    obtenerDatos(offset);
                }
            }
        });

    }

    public void procesarRespuesta(Response<PokemonRespuesta> response) {
        // aqui manipularemos la informacion y la agregaremos a la
        // interfaz grafica

        // Obtener el arreglo de objetos del response
        ArrayList<Pokemon> lista = response.body().getResults();

        aptoParaCargar = true;

        adaptador.agregarElementos(lista);

    }

    private void obtenerDatos(int offset) {
        new PokemonService(MainActivity.this).consultarListaPokemon(limit, offset);
    }
}
