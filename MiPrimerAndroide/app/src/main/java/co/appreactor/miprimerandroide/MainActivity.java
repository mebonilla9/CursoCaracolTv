package co.appreactor.miprimerandroide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtNombre;
    private TextView textView;
    private android.widget.Button btnSaludar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // obtener componente de un layout
        this.btnSaludar = findViewById(R.id.btnSaludar);
        this.textView = findViewById(R.id.textView);
        this.txtNombre = findViewById(R.id.txtNombre);
    }
    
    public void clickSaludar(View v){
        Toast.makeText(MainActivity.this, "Hola"+this.txtNombre.getText().toString(), Toast.LENGTH_LONG).show();
    }
}
