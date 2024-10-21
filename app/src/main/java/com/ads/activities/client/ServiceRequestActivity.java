package com.ads.activities.client;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.providers.RequestProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.project.ads.R;

import java.util.HashMap;
import java.util.Map;

public class ServiceRequestActivity extends AppCompatActivity {

    private RequestProvider mRequestProvider;
    private TextInputEditText addressInput;
    private AutoCompleteTextView serviceTypeInput;
    private TextInputEditText descriptionInput;
    private MaterialButton sendRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        initViews();
        setupServiceTypeDropdown();
        setupSendRequestButton();

        mRequestProvider = new RequestProvider();
    }

    private void initViews() {
        addressInput = findViewById(R.id.et_address);
        serviceTypeInput = findViewById(R.id.spinner_service_type);
        descriptionInput = findViewById(R.id.et_description);
        sendRequestButton = findViewById(R.id.btn_send_request);
    }

    private void setupServiceTypeDropdown() {
        String[] items = getResources().getStringArray(R.array.service_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        serviceTypeInput.setAdapter(adapter);
    }

    private void setupSendRequestButton() {
        sendRequestButton.setOnClickListener(v -> {
            if (validateInputs()) {
                sendServiceRequest();
            }
        });
    }

    private boolean validateInputs() {
        // Validar que los campos no estén vacíos
        if (addressInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa la dirección.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (descriptionInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa una descripción.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendServiceRequest() {
        String address = addressInput.getText().toString();
        String description = descriptionInput.getText().toString();
        String serviceType = serviceTypeInput.getText().toString();

        submitRequest(address, description, serviceType);
    }

    private void submitRequest(String address, String description, String serviceType) {
        String clientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("client_id", clientId);
        requestData.put("address", address);
        requestData.put("description", description);
        requestData.put("service_type", serviceType);

        mRequestProvider.createRequest(requestData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Solicitud creada con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, VerifyRequestActivity.class);
                    intent.putExtra("worker_photo", R.drawable.workerlogo);
                    intent.putExtra("worker_name", "Nombre del Trabajador");
                    intent.putExtra("address", address);
                    intent.putExtra("service_type", serviceType);
                    intent.putExtra("amount", "Monto del Servicio");
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al crear la solicitud: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
