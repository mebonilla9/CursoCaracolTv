package co.appreactor.agendaandroid.negocio.actividades;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.modelo.entidades.Persona;
import co.appreactor.agendaandroid.modelo.servicio.Archivo;
import co.appreactor.agendaandroid.negocio.adaptadores.AdaptadorPersona;
import co.appreactor.agendaandroid.negocio.utilidades.AlertaUtil;
import co.appreactor.agendaandroid.negocio.utilidades.PreferenciasUtil;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputLayout txtBuscar;
    private ListView lstPersonas;

    private final int codigoPermiso = 666;
    private final String[] permisos = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // lista original que contiene todos los datos del archivo plano
    private List<Persona> listaPersonas;

    // lista de datos filtrados a traves del evento textChange
    private List<Persona> listaTemporal;

    private Archivo archivador;

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
        archivador = PreferenciasUtil.obtenerArchivo(
                MainActivity.this
        );
        asignarEventos();
        asignarPermisos();
    }

    private void asignarPermisos() {
        // Validar que el permiso no ha sido concedido aun
        int permisoConcedido = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permisoConcedido != PackageManager.PERMISSION_GRANTED){
            // Solicitud del permiso en tiempo de ejecucion
            ActivityCompat.requestPermissions(
                    this,
                    permisos,
                    codigoPermiso
                    );
            return;
        }
        llenarLista();
    }

    private void asignarEventos() {
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambio o redireccionamiento de una actividad a otra
                Intent irNuevo = new Intent(MainActivity.this,NuevoActivity.class);
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
        if (listaTemporal != null && !listaTemporal.isEmpty()){
            Persona personaTemporal = listaTemporal.get(posicionEliminar);
            // buscar en la lista original el objeto cargado como temporal
            for(Persona iterador : listaPersonas){
                if (personaTemporal.equals(iterador)){
                    listaPersonas.remove(iterador);
                    break;
                }
            }
        } else {
            listaPersonas.remove(posicionEliminar);
        }
        archivador.escribir(listaPersonas);
        txtBuscar.getEditText().setText("");
        llenarLista();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == codigoPermiso &&
                (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ){
            llenarLista();
        }
    }

    private void llenarLista(){
        // cargar la informacion del archivo plano
        listaPersonas = archivador.leer();
        // Crear un nuevo objeto del adaptador para el listView
        AdaptadorPersona adaptador = new AdaptadorPersona(
                MainActivity.this,listaPersonas
        );
        lstPersonas.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflar el xml del menu
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(
                        new Intent(
                                MainActivity.this,
                                ConfiguracionActivity.class
                        )
                );
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
