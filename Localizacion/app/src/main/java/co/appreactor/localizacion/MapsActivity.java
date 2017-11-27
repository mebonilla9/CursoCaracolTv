package co.appreactor.localizacion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager manejador;

    private final int codigoPermiso = 554;
    private final String[] permisos = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // obtener el administrador de localizacion del sistema
        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);

        // disparar una sentencia de actualizacion de la posicion usando el gps
        solicitarLocalizacion();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null){
            Toast.makeText(this, "No te encuentro, donde estas?", Toast.LENGTH_SHORT).show();
            return;
        }
        // obtener la posicion del usuario (Latitud y longitud)
        LatLng ubicacion = new LatLng(location.getLatitude(),location.getLongitude());
        // agregar marca de mapa con nuestra posicion actual
        mMap.clear();
        mMap.addMarker(
                new MarkerOptions()
                .title("Tu Estas aqui!")
                // utilizar un recurso vectorial para visualizar la posicion
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_coqueto))
                .position(ubicacion)
        );
        // Mover la camara hasta nuestra posicion actual
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
        // Animar el movimiento de la camara para que la transicion no sea
        // brusquita
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void obtenerPosicion(Location location) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != codigoPermiso) {
            return;
        }

        if (
                grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED
                ) {
            Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_LONG).show();

        }
    }

    private void solicitarLocalizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MapsActivity.this,
                    permisos,
                    codigoPermiso
            );
            return;
        }
        manejador.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                4000,
                1,
                this);
    }
}
