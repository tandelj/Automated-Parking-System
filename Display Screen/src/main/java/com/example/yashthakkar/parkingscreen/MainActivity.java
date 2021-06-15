package com.example.yashthakkar.parkingscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mdata;
    private TextView slots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slots=(TextView)findViewById(R.id.slots);
        mdata= FirebaseDatabase.getInstance().getReference().child("Ahemdabad").child("Kakaria_lake").child("Area1").child("slots");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s=dataSnapshot.getValue().toString();
                slots.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
