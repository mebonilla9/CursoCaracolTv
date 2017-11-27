package co.appreactor.eventos.negocio.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import co.appreactor.eventos.R;
import co.appreactor.eventos.persistencia.entidades.Sala;

/**
 * Created by lord_nightmare on 9/11/17.
 */

public class AdaptadorSala extends RecyclerView.Adapter<AdaptadorSala.ViewHolder>{

    private List<Sala> dataSet;
    private Context contexto;

    public AdaptadorSala(List<Sala> dataSet, Context contexto) {
        this.dataSet = dataSet;
        this.contexto = contexto;
    }

    /**
     * Asignacion o inflado de la clase interna ViewHolder usando el layout
     * personalizada "item_sala" para generar la correcta visualización de los
     * datos.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar un objeto de vista con la referencia de layout escrito en
        // xml
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sala,parent,false);
        // asignar vista a la clase interna ViewHolder
        return new ViewHolder(vista);
    }

    /**
     * Carga de los datos en un nuevo item basado en el layout xml "item_sala
     * este proceso lo repite por cada posicion del dataset
     *
     * @param holder referencia de la clase interna que representa el layout en
     *               xml
     * @param position representa la posicion actual que se encuentra recorriendo
     *                 el adapter del dataset definido como atributo de clase
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // obtener referencia del objeto que reside en el dataset con la posicion
        // actual
        Sala salaActual = dataSet.get(position);
        // asignacion de valores a los elementos del holder segun el item
        // actual del dataset
        holder.txtNombreSala.setText(salaActual.getNombre());
        holder.txtDireccionSala.setText(salaActual.getDireccion());
        holder.txtTelefonoSala.setText(salaActual.getTelefono());

        // carga asincrona de imagen publica con glide
        RequestOptions op = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(contexto)
                .load("https://vignette3.wikia.nocookie.net/leagueoflegends/images/f/fd/LoL_Battle_8.jpg/revision/latest/scale-to-width-down/2000?cb=20160910184622")
                .apply(op)
                .into(holder.imgSala);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * Clase que representa una instancia del item a renderizar
     * en el recyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        // atributos que representan lo views con cambios en tiempo
        // de ejecucion del layout "item_sala"

        private ImageView imgSala;
        private TextView txtNombreSala;
        private TextView txtDireccionSala;
        private TextView txtTelefonoSala;

        /**
         * Permite la vinculacion de los atributos de clase con su
         * contraparte generada en el layout item_lista
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            imgSala = (ImageView) itemView.findViewById(R.id.imgSala);
            txtNombreSala = (TextView) itemView.findViewById(R.id.txtNombreSala);
            txtDireccionSala = (TextView) itemView.findViewById(R.id.txtDireccionSala);
            txtTelefonoSala = (TextView) itemView.findViewById(R.id.txtTelefonoSala);
        }
    }

}
