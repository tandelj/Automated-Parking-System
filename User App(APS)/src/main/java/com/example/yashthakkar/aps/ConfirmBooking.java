package com.example.yashthakkar.aps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmBooking extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    Button mapbutton,paymentbutton;
    long selected;
    String pay=null;

   // String location[]=new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

       // Bundle location=getIntent().getExtras();
        //String[] locationx = location.getStringArrayList("location").toArray(new String[0]);

        Bundle b=this.getIntent().getExtras();
        final String[] array=b.getStringArray("location");
        TextView locationtext=(TextView)findViewById(R.id.locationtext);
        locationtext.setText("Location selected :"+array[1]+","+array[0]);
        TextView paytext=(TextView)findViewById(R.id.paytext);
       // paytext.setText("You need to pay :"+array[2]);
        spinner = (Spinner) findViewById(R.id.spinnervehicle);
        adapter = ArrayAdapter.createFromResource(this, R.array.Vehicle, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " selected", Toast.LENGTH_LONG).show();
                selected=adapterView.getItemIdAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(selected==0){
            pay=Integer.toString(Integer.parseInt(array[2])*30);

        }
        else{
            pay=Integer.toString(Integer.parseInt(array[2])*20);
        }

        paytext.setText("You need to pay Rs: "+pay);

        mapbutton=(Button)findViewById(R.id.mapbutton);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(ConfirmBooking.this,MapsActivity.class));

                Bundle b=new Bundle();
                b.putStringArray("locationx", new String[]{array[0], array[1]});
                Intent i=new Intent(ConfirmBooking.this,MapsActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        paymentbutton=(Button)findViewById(R.id.paymentbutton);
        paymentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmBooking.this,Payment.class));
            }
        });
    }


}
