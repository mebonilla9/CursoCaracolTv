package co.appreactor.agendaandroid.negocio.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.negocio.utilidades.PreferenciasUtil;

public class ConfiguracionActivity extends AppCompatActivity {

    private Spinner cboFormato;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        this.btnGuardar = findViewById(R.id.btnGuardar);
        this.cboFormato = findViewById(R.id.cboFormato);

        // asignar datos a spinner usando un Array Adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                ConfiguracionActivity.this,
                R.array.formatos,
                android.R.layout.simple_spinner_dropdown_item
        );

        cboFormato.setAdapter(adapter);

        // Seleccionar la opcion guardada en preferencias
        String formato = PreferenciasUtil.leerPreferencias(
                "formato",
                ConfiguracionActivity.this);

        switch (formato){
            case "Json":
                cboFormato.setSelection(0);
                break;
            case "XML - (SAX)":
                cboFormato.setSelection(1);
                break;
        }

        // asignacion del evento click para boton de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtener el valor seleccionado del spinner
                String formato = cboFormato.getSelectedItem().toString();
                PreferenciasUtil.guardarPreferencia(
                        "formato",
                        formato,
                        ConfiguracionActivity.this
                );
                // traladar a MainActivity
                startActivity(new Intent(
                        ConfiguracionActivity.this,
                        MainActivity.class
                ));
            }
        });
    }
}
