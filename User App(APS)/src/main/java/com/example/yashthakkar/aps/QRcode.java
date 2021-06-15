package com.example.yashthakkar.aps;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
public class QRcode extends AppCompatActivity {
    FirebaseAuth mauth;
    TextView transaction;
    ImageView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mauth=FirebaseAuth.getInstance();
        String uid=mauth.getCurrentUser().getUid();
        transaction=(TextView)findViewById(R.id.transaction);
        qrcode=(ImageView)findViewById(R.id.qrcode);
        transaction.setText("Order Id: "+uid);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(uid, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcode.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }
        Toast.makeText(QRcode.this,"thank you for using Automatic Parking System",Toast.LENGTH_LONG);
    }

}
