package co.appreactor.noticias.negocio.actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import co.appreactor.noticias.R;
import co.appreactor.noticias.negocio.util.FragmentUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navview;
    private DrawerLayout drawerlayout;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.design.widget.FloatingActionButton fab;
    private android.widget.FrameLayout flContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.flContenedor = (FrameLayout) findViewById(R.id.flContenedor);
        this.drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navview = (NavigationView) findViewById(R.id.nav_view);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        navview.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentUtil.obtenerFragmento(id, null, getSupportFragmentManager().beginTransaction(), R.id.flContenedor);

        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
