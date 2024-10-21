package com.ads.activities.worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ads.activities.MainActivity;
import com.ads.providers.AuthProvider;
import com.project.ads.R;

public class HomeWorkerActivity extends AppCompatActivity {
    Button mButtonViewMap2;
    Button mButtonFixDepot;
    AuthProvider mAuthProvider;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_worker);

        mAuthProvider = new AuthProvider();
        mButtonViewMap2 = findViewById(R.id.vermapaworker);
        mButtonFixDepot = findViewById(R.id.buttonFixDepot);
        mToolbar = findViewById(R.id.toolbar_color);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

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

        // Configurar el color de la barra de estado al color por defecto del sistema
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
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
}