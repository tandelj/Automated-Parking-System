package com.example.yashthakkar.aps;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat[]=new double[2];
    private double lng[]=new double[2];
    private DatabaseReference db,db2;
    //private String latt;
   // private String lngt;
    private TextView txt;
  //  private TextView txt1;
   // private TextView txt2;
   // Double l1,l2;
    String lata[]=new String[2];
    String lnga[]=new String[2];

    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Bundle b=this.getIntent().getExtras();
        final String[] array=b.getStringArray("locationx");
        String area[]={"Area1","Area2"};


             txt= (TextView) findViewById(R.id.txt);

            txt.setText(array[0]+","+array[1]);

          db = FirebaseDatabase.getInstance().getReference().child("Ahemdabad").child(array[1]).child(area[0]);
           ValueEventListener valueEventListener = db.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {


                   // lat=(Double)dataSnapshot.child("lat").getValue();
                   //lng=(Double)dataSnapshot.child("lng").getValue();
                   //latt=dataSnapshot.getValue().toString();

                   Map<String, String> map = (Map) dataSnapshot.getValue();

                   lata[0] = map.get("lat").trim().toString();
                   lnga[0] = map.get("lng").trim().toString();
                   Log.v("E_value", "Latt :" + lata[0]);
                   Log.v("E_value", "Lngt :" + lnga[0]);
                  // txt.setText("got: " + latt + " " + lngt);
                   try {
                       lat[0] = Double.parseDouble(lata[0]);
                       lng[0] = Double.parseDouble(lnga[0]);
                      // txt1.setText("Parsed Value Inside Try: LaT:" + lat + " LnG:" + lng);
                      // l1 = lat;

                       //l2 = lng;

                       //LatLng my =new LatLng(lat,lng);
                   } catch (NullPointerException e) {
                      // txt1.setText("NULL VALUE");

                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
        db2 = FirebaseDatabase.getInstance().getReference().child("Ahemdabad").child(array[1]).child(area[1]);
        ValueEventListener valueEventListen = db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();

                lata[1] = map.get("lat").trim().toString();
                lnga[1] = map.get("lng").trim().toString();
                Log.v("E_value", "Latt :" + lata[1]);
                Log.v("E_value", "Lngt :" + lnga[1]);
                // txt.setText("got: " + latt + " " + lngt);
                try {
                    lat[1] = Double.parseDouble(lata[1]);
                    lng[1] = Double.parseDouble(lnga[1]);
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);

                } catch (NullPointerException e) {


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Marker source,destination;



        LatLng  my =new LatLng(lat[0],lng[0]);
        LatLng  my1 =new LatLng(lat[1],lng[1]);
        /*this code for zooming Camera*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(my, 15),5000,null);
         /*  source= mMap.addMarker(new MarkerOptions().position(my1).title("Parker here"));
             CameraPosition cameraPosition1=new CameraPosition.Builder().target(my).zoom(17).build();
             mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(my1));

            mMap.addMarker(new MarkerOptions().position(my).title("Park here"));
            CameraPosition cameraPosition=new CameraPosition.Builder().target(my).zoom(17).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(my));*/


        source = mMap.addMarker(new MarkerOptions()
                .position(my)
                .title("park here"));

                 source.setTag(0);

        destination = mMap.addMarker(new MarkerOptions()
                .position(my1)
                .title("park here"));
                 destination.setTag(0);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                {
                 startActivity(new Intent(MapsActivity.this,ConfirmBooking.class));


                }
                return false;
            }
        });

    }


}


