package com.ads.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RequestProvider {

    private DatabaseReference mDatabaseReference;

    public RequestProvider() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("requests");
    }

    /**
     * Crea una nueva solicitud en la base de datos.
     *
     * @param requestData Un mapa con los datos de la solicitud.
     * @return Tarea para manejar el resultado de la operación.
     */
    public Task<Void> createRequest(Map<String, Object> requestData) {
        String requestId = mDatabaseReference.push().getKey(); // Genera un ID único para la solicitud
        return mDatabaseReference.child(requestId).setValue(requestData); // Guarda los datos en la base de datos
    }

    /**
     * Lee todas las solicitudes de la base de datos.
     *
     * @param callback Callback para manejar los datos de las solicitudes.
     */
    public void getRequests(ValueEventListener callback) {
        mDatabaseReference.addValueEventListener(callback); // Añade un listener para recibir datos en tiempo real
    }

    /**
     * Lee una solicitud específica de la base de datos.
     *
     * @param requestId El ID de la solicitud a leer.
     * @param callback Callback para manejar los datos de la solicitud.
     */
    public void getRequest(String requestId, ValueEventListener callback) {
        mDatabaseReference.child(requestId).addValueEventListener(callback); // Añade un listener para recibir datos de una solicitud específica
    }
}
