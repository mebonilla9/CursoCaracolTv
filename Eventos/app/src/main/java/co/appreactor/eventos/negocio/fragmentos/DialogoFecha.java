package co.appreactor.eventos.negocio.fragmentos;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import co.appreactor.eventos.persistencia.dto.FechaDTO;

/**
 * Created by lord_nightmare on 9/11/17.
 */

@SuppressLint("ValidFragment")
public class DialogoFecha extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText textoFecha;
    private FechaDTO fecha;

    public DialogoFecha(EditText textoFecha, FechaDTO fecha) {
        this.textoFecha = textoFecha;
        this.fecha = fecha;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Obtener una instancia del calendario del sistema
        Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, anio, mes, dia);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fechaSeleccionada = year + " - "
                + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + " - "
                + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
        textoFecha.setText(fechaSeleccionada);
        // obtener la fecha en formato Unix
        Calendar calendario = Calendar.getInstance();
        calendario.set(year, month, dayOfMonth);
        // obtencion del long de fecha en formato unix
        fecha.setFechaUnix(calendario.getTimeInMillis());
    }
}
