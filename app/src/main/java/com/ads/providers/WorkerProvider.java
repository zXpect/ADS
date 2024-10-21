package com.ads.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ads.models.Worker;

import java.util.HashMap;
import java.util.Map;

public class WorkerProvider {
    DatabaseReference mDataBase;

    public WorkerProvider() {
        mDataBase = FirebaseDatabase.getInstance().getReference().child("User").child("Trabajadores");
    }

    public Task<Void> create(Worker worker){
        Map<String, Object> map = new HashMap<>();
        map.put("name", worker.getName());
        map.put("lastName", worker.getName());
        map.put("email", worker.getName());
        map.put("work", worker.getWork());
        return mDataBase.child(worker.getId()).setValue(worker);
    }

    public Task<DataSnapshot> getWorker(String id) {
        return mDataBase.child(id).get();
    }

}
