package co.appreactor.agendasqlite.negocio.actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import co.appreactor.agendasqlite.R;
import co.appreactor.agendasqlite.modelo.entidades.Persona;

public class DetalleActivity extends AppCompatActivity {

    private TextView txtDetalleCorreo;
    private TextView txtDetalleTelefono;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        this.txtDetalleTelefono = findViewById(R.id.txtDetalleTelefono);
        this.txtDetalleCorreo = findViewById(R.id.txtDetalleCorreo);
        this.fab = findViewById(R.id.fab);
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // obtener los extras del intent redireccionado
        Persona personaRecibida = (Persona) getIntent().getSerializableExtra("persona");

        // Asignar el nombre de la persona al titulo de la toolbar del activity
        getSupportActionBar().setTitle(personaRecibida.getNombre());

        txtDetalleCorreo.setText(personaRecibida.getCorreo());
        txtDetalleTelefono.setText(personaRecibida.getTelefono());
    }
}
