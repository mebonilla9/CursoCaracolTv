package co.appreactor.agendasqlite.negocio.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.appreactor.agendasqlite.R;
import co.appreactor.agendasqlite.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 2/11/17.
 */

public class AdaptadorPersona extends BaseAdapter {

    private Context contexto;
    private List<Persona> dataSet;

    public AdaptadorPersona(Context contexto, List<Persona> dataSet) {
        this.contexto = contexto;
        this.dataSet = dataSet;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataSet.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // evaluar si el objeto convertView existe
        if (convertView == null){
            convertView = LayoutInflater.from(contexto)
                    .inflate(R.layout.item_lista,null);
        }

        // obtener referencia del label en el item_lista
        TextView txtNombrePersona = convertView.findViewById(R.id.txtNombrePersona);
        TextView txtCorreoPersona = convertView.findViewById(R.id.txtCorreoPersona);

        Persona personaLeer = dataSet.get(position);

        txtNombrePersona.setText(personaLeer.getNombre());
        txtCorreoPersona.setText(personaLeer.getCorreo());

        return convertView;
    }
}
