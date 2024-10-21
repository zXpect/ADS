package com.ads.activities.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import com.project.ads.R;

public class HomeUserActivity extends AppCompatActivity {
    Button mButtonViewMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        mButtonViewMap = findViewById(R.id.vermapa);
        mButtonViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMapClient();
            }

            private void viewMapClient() {
                Intent intent = new Intent(HomeUserActivity.this, MapClientActivity.class);
                startActivity(intent);
            }

        });

        // Configurar el color de la barra de estado al color por defecto del sistema
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }
}