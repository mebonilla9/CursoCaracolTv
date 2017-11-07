package co.appreactor.agendaandroid.negocio.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.modelo.entidades.Persona;
import co.appreactor.agendaandroid.modelo.servicio.Archivo;
import co.appreactor.agendaandroid.modelo.servicio.ArchivoJson;
import co.appreactor.agendaandroid.negocio.utilidades.AlertaUtil;

public class NuevoActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextInputLayout txtNombre;
    private TextInputLayout txtCorreo;
    private TextInputLayout txtTelefono;
    private Button btnGuardar;

    private Archivo archivador;

    private DialogInterface.OnClickListener clickAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        this.btnGuardar = findViewById(R.id.btnGuardar);
        this.txtTelefono = findViewById(R.id.txtTelefono);
        this.txtCorreo = findViewById(R.id.txtCorreo);
        this.txtNombre = findViewById(R.id.txtNombre);
        this.imageView = findViewById(R.id.imageView);
        archivador = new ArchivoJson();
        asignarEventos();
    }

    private void asignarEventos() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona personaGuardar = new Persona();
                personaGuardar.setNombre(txtNombre.getEditText().getText().toString());
                personaGuardar.setCorreo(txtCorreo.getEditText().getText().toString());
                personaGuardar.setTelefono(txtTelefono.getEditText().getText().toString());

                // leer la estructura actual del archivo plano
                List<Persona> lista = archivador.leer();
                lista.add(personaGuardar);
                archivador.escribir(lista);

                // Invocar a la alerta
                AlertaUtil.mostrarAlerta("Guardar persona",
                        "Ha sido agregada una nueva persona",
                        clickAceptar,null,NuevoActivity.this);
            }
        });

        // Asignar valor al evento click para dialogo
        clickAceptar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // movernos a MainActivity usando un intent
                startActivity(new Intent(
                        NuevoActivity.this,
                        MainActivity.class));
            }
        };
    }
}
