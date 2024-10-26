package com.ads.activities.worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.ads.activities.MainActivity;
import com.ads.activities.client.HomeUserActivity;
import com.ads.includes.MyToolbar;
import com.ads.providers.AuthProvider;
import com.ads.providers.GeofireProvider;
import com.ads.providers.WorkerProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.project.ads.R;

public class HomeWorkerActivity extends AppCompatActivity {
    Button mButtonViewMap2;
    Button mButtonFixDepot;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    AuthProvider mAuthProvider;
    Toolbar mToolbar;
    private FusedLocationProviderClient mFusedLocation;
    private GeofireProvider mGeofireProvider;
    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_worker);
        initProviders();

        // Inicializar vistas del Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.toolbar);


        // Configurar Toolbar
        setSupportActionBar(mToolbar);

        // Configurar Navigation Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mButtonViewMap2 = findViewById(R.id.vermapaworker);
        mButtonFixDepot = findViewById(R.id.buttonFixDepot);


        mButtonViewMap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap();
            }

        });

        mButtonFixDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFixDepot();
            }
        });

        // Configurar listener para los items del menú
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(HomeWorkerActivity.this, "Inicio", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(HomeWorkerActivity.this, "Perfil", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(HomeWorkerActivity.this, "Configuración", Toast.LENGTH_SHORT).show();
                }else if (id == R.id.action_logout) {
                    logout();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Configurar el color de la barra de estado al color por defecto del sistema
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }
    private void initProviders() {
        mAuthProvider = new AuthProvider();
        mGeofireProvider = new GeofireProvider();
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
    }

    private void viewMap() {
        Intent intent = new Intent(HomeWorkerActivity.this, MapWorkerActivity.class);
        startActivity(intent);
    }

    private void goToFixDepot() {
        Intent intent = new Intent(HomeWorkerActivity.this, FixDepotActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.worker_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mAuthProvider.logOut();
            Intent intent = new Intent(HomeWorkerActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void disconnect() {
        if (mFusedLocation != null) {
            mFusedLocation.removeLocationUpdates(mLocationCallback);
            mGeofireProvider.removeLocation(mAuthProvider.getId());
        } else {
            Toast.makeText(this, "No se puede desconectar", Toast.LENGTH_SHORT).show();
        }
    }
    private void logout() {
        disconnect();
        mAuthProvider.logOut();
        Intent intent = new Intent(HomeWorkerActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}