package co.appreactor.noticias.negocio.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.appreactor.noticias.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListarCategoriaFragment extends Fragment {


    public ListarCategoriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar_categoria, container, false);
    }

}
