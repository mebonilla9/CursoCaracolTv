package co.appreactor.eventos.negocio.fragmentos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.appreactor.eventos.R;
import co.appreactor.eventos.negocio.actividades.MainActivity;
import co.appreactor.eventos.persistencia.dto.FechaDTO;
import co.appreactor.eventos.persistencia.dto.RespuestaDTO;
import co.appreactor.eventos.persistencia.entidades.Evento;
import co.appreactor.eventos.persistencia.entidades.Sala;
import co.appreactor.eventos.persistencia.entidades.TipoEvento;
import co.appreactor.eventos.persistencia.servicio.ServicioHttp;

/**
 * Created by lord_nightmare on 8/11/17.
 */

public class RegistrarEventoFragment extends Fragment implements Handler.Callback{

    private TextInputLayout txtNombre;
    private TextInputLayout txtFecha;
    private TextInputLayout txtHoraInicio;
    private TextInputLayout txtHoraFin;

    private Spinner spTipoEvento;
    private Spinner spSala;

    private List<TipoEvento> listaTipoEvento;
    private List<Sala> listaSalas;

    private FechaDTO fecha;

    private FloatingActionButton fab;
    private Handler puente;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_registrar_evento,container,false);
        this.spSala = (Spinner) vista.findViewById(R.id.spSala);
        this.spTipoEvento = (Spinner) vista.findViewById(R.id.spTipoEvento);
        this.txtHoraFin = (TextInputLayout) vista.findViewById(R.id.txtHoraFin);
        this.txtHoraInicio = (TextInputLayout) vista.findViewById(R.id.txtHoraInicio);
        this.txtFecha = (TextInputLayout) vista.findViewById(R.id.txtFecha);
        this.txtNombre = (TextInputLayout) vista.findViewById(R.id.txtNombre);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        puente = new Handler(this);
        fecha = new FechaDTO();
        // Carga de spinner con informacion de la base de datos
        llenarSpinners();
        return vista;
    }

    private void llenarSpinners() {
        // Obtener con TypeToken de Gson el tipo de datos al que convertimos la 
        // respuesta del servidor
        
        // TipoEvento
        Type tipoEvento = new TypeToken<RespuestaDTO<List<TipoEvento>>>(){}.getType();
        ServicioHttp.invocar("",null,"POST",
                tipoEvento, puente,"/tipoevento/consultar");
        
        // Sala
        Type sala = new TypeToken<RespuestaDTO<List<Sala>>>(){}.getType();
        ServicioHttp.invocar("",null,"POST",
                sala, puente,"/sala/consultar");
        
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Registrar Evento");
        ((MainActivity) getActivity()).getSupportActionBar()
                .setSubtitle("Usa es intefaz para registrar un eventos");
        
        // forma de ocultar controles desde programacion
        // fab.setVisibility(View.GONE);
        
        // fab.setVisibility(View.VISIBLE);
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarEvento();
            }
        });
        asignarEventos();
    }

    private void registrarEvento() {
        if (spTipoEvento.getSelectedItemPosition() < 1){
            Toast.makeText(
                    getActivity(),
                    "Debe seleccionar un tipo de evento",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }
        if (spSala.getSelectedItemPosition() < 1){
            Toast.makeText(
                    getActivity(),
                    "Debe seleccionar una sala",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        Evento gEvento = new Evento();
        gEvento.setNombre(txtNombre.getEditText().getText().toString());
        gEvento.setFecha(new Date(fecha.getFechaUnix()));
        gEvento.setHoraInicio(txtHoraInicio.getEditText().getText().toString());
        gEvento.setHoraFin(txtHoraFin.getEditText().getText().toString());

        // obtener la posicion seleccionada del spinner Tipo Evento
        TipoEvento te = listaTipoEvento.get(
                spTipoEvento.getSelectedItemPosition() - 1
        );
        gEvento.setTipoEvento(te);

        // obtener la posicion seleccionada del spinner Sala
        Sala sa = listaSalas.get(
                spSala.getSelectedItemPosition() - 1
        );
        gEvento.setSala(sa);

        // Enviar peticicion al servidor para guardar evento

        Type tipoRegistarEvento = new TypeToken<RespuestaDTO>(){}.getType();

        ServicioHttp.invocar(
                gEvento,
                null,
                "POST",
                tipoRegistarEvento,
                puente,
                "/evento/insertar"
        );
    }

    private void asignarEventos(){
        View.OnFocusChangeListener eventoFoco = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    return;
                }
                DialogFragment dialogo = null;
                if (view.equals(txtFecha.getEditText())){
                    dialogo = new DialogoFecha((EditText) view,fecha);
                } else {
                    dialogo = new DialogoHora((EditText) view);
                }
                // Ocultar el teclado para visualizar el Dialogo por completo
                view.clearFocus();

                // obtener instancia de transaccion de fragmentos
                FragmentTransaction transaccion = getChildFragmentManager().beginTransaction();
                dialogo.show(transaccion,"Seleccionar");
            }
        };

        // Asignacion de listener compartido a los objetos que van a utilizarlo
        txtFecha.getEditText().setOnFocusChangeListener(eventoFoco);
        txtHoraInicio.getEditText().setOnFocusChangeListener(eventoFoco);
        txtHoraFin.getEditText().setOnFocusChangeListener(eventoFoco);
    }

    @Override
    public boolean handleMessage(Message message) {
        RespuestaDTO respuesta = (RespuestaDTO) message.obj;
        // Verificar que el codigo de la respuesta sea 1 (exitoso)
        if (respuesta.getCodigo() != 1){
            Toast.makeText(getActivity(), respuesta.getMensaje(), Toast.LENGTH_LONG).show();
            return false;
        }
        switch (respuesta.getRuta()){
            case "/tipoevento/consultar":
                cargarTipoEventos(respuesta);
                break;
            case "/sala/consultar":
                cargarSalas(respuesta);
                break;
            case "/evento/insertar":
                resultadoRegistrarEvento(respuesta);
                break;
        }
        return false;
    }

    private void resultadoRegistrarEvento(RespuestaDTO respuesta) {
        txtNombre.getEditText().setText("");
        txtFecha.getEditText().setText("");
        txtHoraInicio.getEditText().setText("");
        txtHoraFin.getEditText().setText("");
        spTipoEvento.setSelection(0);
        spSala.setSelection(0);

        Snackbar.make(fab,respuesta.getMensaje(),Snackbar.LENGTH_LONG)
                .setAction("Aceptar",null).show();
    }

    private void cargarTipoEventos(RespuestaDTO<List<TipoEvento>> respuesta) {
        List<String> tipoEventos = new ArrayList<>();
        // asignar la posicion seleccione
        tipoEventos.add("Seleccione..");

        // cargar lista de clase con objetos de la respuesta
        listaTipoEvento = respuesta.getDatos();

        // cargar lista de strings para el spinner
        for (TipoEvento iterador : listaTipoEvento){
            tipoEventos.add(iterador.getNombre());
        }

        spTipoEvento.setAdapter(new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                tipoEventos
        ));
    }

    private void cargarSalas(RespuestaDTO<List<Sala>> respuesta) {
        List<String> tipoSalas = new ArrayList<>();
        // asignar la posicion seleccione
        tipoSalas.add("Seleccione..");

        // cargar lista de clase con objetos de la respuesta
        listaSalas = respuesta.getDatos();

        // cargar lista de strings para el spinner
        for (Sala iterador : listaSalas){
            tipoSalas.add(iterador.getNombre());
        }

        spSala.setAdapter(new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                tipoSalas
        ));
    }
}
