package co.appreactor.componentes;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TableLayout tblProductos;
    private ListView lstProductos;
    private Button btnEliminar;

    // representar el dataset de datos para un adaptador
    private ArrayList<String> listaProductos = new ArrayList<>();

    // Adaptador para el listview que sera mostrado en la orientacion portrait
    private ArrayAdapter<String> adaptador;

    private final Context contexto = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Se verifica si es la primera vez que se va a ejecutar la aplicacion
        if (savedInstanceState == null) {
            llenarListaProductos();
        }

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            this.lstProductos = (ListView) findViewById(R.id.lstProductos);
            this.btnEliminar = (Button) findViewById(R.id.btnEliminar);
            llenarListView();
            this.btnEliminar.setOnClickListener(clickVertical);
        } else {
            this.tblProductos = (TableLayout) findViewById(R.id.tblProductos);
            llenarTabla();
        }
    }

    private void llenarTabla() {
        // llenar tabla segun la longitud del dataset
        for (int i = 0; i < listaProductos.size(); i++) {
            // obtener el nombre del producto del dataset
            String nombreProducto = listaProductos.get(i);
            // Crear un view de tipo TableRow de manera dinamica
            TableRow fila = new TableRow(contexto);

            // agregar un textView para visualizar el nombre del producto
            TextView producto = new TextView(contexto);
            producto.setText(nombreProducto);

            // agregar al TextView como hijo del TableRow
            fila.addView(producto);

            Button botonDinamico = new Button(contexto);
            botonDinamico.setText("Eliminar");

            // Agregar como etiqueta de dato el nombre del producto que el boton
            // podria eliminar
            botonDinamico.setTag(nombreProducto);

            // Agregar un evento de Click
            botonDinamico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // obtener el tag del producto de la fila que seleccionamos
                    String nombre = (String) v.getTag();

                    for (int i = 0; i < listaProductos.size(); i++) {
                        if (listaProductos.get(i).equals(nombre)) {
                            listaProductos.remove(i);
                        }
                    }
                    // eliminar de manera dinamica una vista de la interfaz grafica
                    tblProductos.removeView((View) v.getParent());
                }
            });

            fila.addView(botonDinamico);

            // agregar la fila a la tabla
            tblProductos.addView(fila);
        }

    }

    private void llenarListView() {
        // generar adaptador de tipo array con un item prefabricado que reside
        // en el sistema operativo, que permite la seleccion multiple de objetos
        adaptador = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_checked,
                listaProductos
        );
        // asignar adaptador al listView
        lstProductos.setAdapter(adaptador);
        // habilitar la seleccion multiple al adaptador
        lstProductos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adaptador.notifyDataSetChanged();
    }

    private void llenarListaProductos() {
        for (int i = 0; i < 20; i++) {
            listaProductos.add("Producto Numero " + i);
        }
    }

    /**
     * metodo que guarda el estado de la instancia de la actividad cuando sufre cambios por
     * orientacion del dispositivo
     * @param outState Representa los argumentos que vamos a persistir para la restauracion de la
     *                 instancia
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("lista_productos",listaProductos);
        super.onSaveInstanceState(outState);
    }

    /**
     * metodo que restaura los valores y estado de la instancia de la actividad cuando sufre cambios
     * por orientacion del dispositivo
     * @param savedInstanceState Representa los argumentos que vamos a persistir para la restauracion de la
     *                           instancia
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        listaProductos = (ArrayList<String>) savedInstanceState.getSerializable("lista_productos");
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            llenarListView();
        } else {
            llenarTabla();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private View.OnClickListener clickVertical = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // obtener en un arrayBooleano la liste de items que contiene el ListView
            SparseBooleanArray itemsSeleccionados = lstProductos.getCheckedItemPositions();

            // Iteracion inversa del arrayBooleano para determinar que objetos del dataset
            // eliminar
            for (int i = itemsSeleccionados.size() - 1; i >= 0; i--) {
                // obtener la llave del item que estamos evaluando
                int llave = itemsSeleccionados.keyAt(i);
                // obtener en un boolean si esta seleccionado o no
                boolean esSeleccionado = itemsSeleccionados.get(llave);
                if (esSeleccionado){
                    // eliminar objeto del dataset si la validacion es verdadera
                    listaProductos.remove(llave);
                }
            }
            // notificar cambios del dataset al adaptador
            adaptador.notifyDataSetChanged();
            // limpiar la seleccion de items del ListView
            lstProductos.clearChoices();
        }
    };


}
