package co.appreactor.eventos.negocio.fragmentos;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import co.appreactor.eventos.R;


public class PruebaFragment extends DialogFragment {


    private android.widget.Spinner spinner;
    private android.widget.RatingBar ratingBar;

    public PruebaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_prueba, container, false);
        this.ratingBar = (RatingBar) vista.findViewById(R.id.ratingBar);
        this.spinner = (Spinner) vista.findViewById(R.id.spinner);
        return vista;
    }

}
