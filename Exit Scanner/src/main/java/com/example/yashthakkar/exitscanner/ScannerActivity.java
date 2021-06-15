package com.example.yashthakkar.exitscanner;


        import android.app.Activity;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;



        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.zxing.integration.android.IntentIntegrator;
        import com.google.zxing.integration.android.IntentResult;

public class ScannerActivity extends AppCompatActivity {

    private DatabaseReference mdata;
    private TextView tv;
    private String contents;
    private String name;
    private View v;
    private ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    final Activity activity =this;
    public void clickable(View view){
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
        builder.setTitle("Title");
        builder.setMessage("Message....");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"YES",Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert =builder.create();
        alert.show();*/

    }

    @Override
    protected void onActivityResult(int requestCode, int  resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents()==null){
                // Toast.makeText(this, "You Cancelled the scanning", Toast.LENGTH_LONG).show();
                contents="null";

            }
            else{
                // Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                contents=result.getContents();
                mdata=FirebaseDatabase.getInstance().getReference().child("users").child(contents);
                valueEventListener=mdata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        name=(String)dataSnapshot.child("name").getValue();
                        Log.v("LOG: ", "NAME: "+name);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                builder.setTitle("Visit Again");
                builder.setMessage("BYE ");
                builder.setCancelable(false);
                //tv.setText(name);
                builder.setPositiveButton("OK To Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"YES",Toast.LENGTH_LONG).show();
                        clickable(v);
                    }
                });

                builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clickable(v);
                    }
                });
                AlertDialog alert =builder.create();
                alert.show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

