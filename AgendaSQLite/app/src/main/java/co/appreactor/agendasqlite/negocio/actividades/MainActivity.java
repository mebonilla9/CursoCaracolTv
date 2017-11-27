package co.appreactor.agendasqlite.negocio.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendasqlite.R;
import co.appreactor.agendasqlite.modelo.dao.PersonaDao;
import co.appreactor.agendasqlite.modelo.entidades.Persona;
import co.appreactor.agendasqlite.negocio.adaptadores.AdaptadorPersona;
import co.appreactor.agendasqlite.negocio.utilidades.AlertaUtil;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputLayout txtBuscar;
    private ListView lstPersonas;

    // lista original que contiene todos los datos del archivo plano
    private List<Persona> listaPersonas;

    // lista de datos filtrados a traves del evento textChange
    private List<Persona> listaTemporal;

    private DialogInterface.OnClickListener clickEliminar;
    private int posicionEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lstPersonas = findViewById(R.id.lstPersonas);
        this.txtBuscar = findViewById(R.id.txtBuscar);
        this.fab = findViewById(R.id.fab);
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        asignarEventos();
        llenarLista();
    }

    private void asignarEventos() {
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambio o redireccionamiento de una actividad a otra
                Intent irNuevo = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(irNuevo);
            }
        });

        // Agregar evento de click de item para el listView
        lstPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visualizarPersona(position);
            }
        });

        // Agregar evento de click sostenido de item para el listView
        lstPersonas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posicionEliminar = position;
                AlertaUtil.mostrarAlerta(
                        "Eliminar Persona",
                        "Esta seguro que desea eliminar el registro",
                        clickEliminar,
                        null,
                        MainActivity.this
                );
                return true;
            }
        });

        // Evento de confirmacion de borrado de persona usando AlertaUtil
        clickEliminar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarContacto();
            }
        };

        // Evento de cambio de texto para el objeto txtBuscar
        txtBuscar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filtrarLista(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void filtrarLista(CharSequence s) {
        listaTemporal = new ArrayList<>();
        for (Persona iterador : listaPersonas){
            if (iterador.getNombre().toLowerCase().contains(s.toString().toLowerCase())){
                listaTemporal.add(iterador);
            }
        }
        // regenerar el listView con un adaptador que reciba la lista filtrada
        lstPersonas.setAdapter(new AdaptadorPersona(MainActivity.this,listaTemporal));
    }

    private void eliminarContacto() {

    }

    private void visualizarPersona(int position) {
        // obtener del dataSet (listaPersonas) el objeto en la misma posicion
        // a la que dimos click en el listView
        Persona personaConsultada = null;
        // validar si la lista temporal existe
        if (listaTemporal != null && !listaTemporal.isEmpty()){
            personaConsultada = listaTemporal.get(position);
        } else {
            personaConsultada = listaPersonas.get(position);
        }
        Intent irDetalle = new Intent(MainActivity.this, DetalleActivity.class);
        // envio de parametros dentro del intent
        irDetalle.putExtra("persona",personaConsultada);
        // redireccionar a DetalleActivity
        startActivity(irDetalle);
    }

    private void llenarLista(){
        // cargar la informacion de la base de datos
        listaPersonas = new PersonaDao(MainActivity.this).consultar();
        // Crear un nuevo objeto del adaptador para el listView
        AdaptadorPersona adaptador = new AdaptadorPersona(
                MainActivity.this,listaPersonas
        );
        lstPersonas.setAdapter(adaptador);
    }

}
