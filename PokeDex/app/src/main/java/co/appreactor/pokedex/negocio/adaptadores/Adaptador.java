package co.appreactor.pokedex.negocio.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.appreactor.pokedex.R;
import co.appreactor.pokedex.persistencia.entidades.Pokemon;

/**
 * Created by lord_nightmare on 20/11/17.
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private ArrayList<Pokemon> dataset;
    private Context contexto;

    public Adaptador(Context contexto){
        this.contexto = contexto;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon item = dataset.get(position);
        holder.txtNombre.setText(item.getName());

        //Acceder a la imagen del pokemon
        Glide.with(contexto)
                .load("http://pokeapi.co/media/sprites/pokemon/"+item.getNumber()+".png")
                .into(holder.imgPokemon);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void agregarElementos(ArrayList<Pokemon> lista){
        this.dataset.addAll(lista);
        notifyDataSetChanged();
    }

    /**
     * La clase view holder representa una plantilla a nivel de logica del negocio
     * su principal funcion es la de indexar la informacion de Dataset del
     * adaptador en un componente grafico escrito en xml
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgPokemon;
        private TextView txtNombre;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPokemon = (ImageView) itemView.findViewById(R.id.imgPokemon);
            txtNombre = (TextView) itemView.findViewById(R.id.txtNombre);
        }
    }
}
