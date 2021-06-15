package com.example.yashthakkar.aps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.view.View.*;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity  {
    private Button map;
    private EditText email,password;
    private Button emailsignin;
    private ProgressDialog mprogress1;
    private FirebaseAuth mauthe;
    private DatabaseReference mfirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mprogress1=new ProgressDialog(this);
        mauthe=FirebaseAuth.getInstance();
        mfirebase=FirebaseDatabase.getInstance().getReference().child("users");
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        emailsignin=(Button)findViewById(R.id.email_sign_in_button);
        Button emailsignup=(Button)findViewById(R.id.signupbutton);
        emailsignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Signup.class);
                startActivity(i);
            }
        });
      /*  map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(i);


            }

        });*/
        emailsignin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Signinup();
            }
        });
    }

        public void Signinup(){
            String emailx=email.getText().toString().trim();
            String passx=password.getText().toString().trim();


            if (emailx.isEmpty()) {
                email.setError("Email is required");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailx).matches()) {
                email.setError("Please enter a valid email");
                email.requestFocus();
                return;
            }

            if (passx.isEmpty()) {
                password.setError("Password is required");
                password.requestFocus();
                return;
            }

            else{
                mprogress1.setMessage("Signing in");
                mprogress1.show();
                mauthe.signInWithEmailAndPassword(emailx,passx).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //Checkuser();

                            Intent RegisterIntent = new Intent(LoginActivity.this, Booking_info.class);
                            RegisterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(RegisterIntent);

                            mprogress1.dismiss();
                        }
                        else
                        {
                            Color.parseColor("#FF0000");
                            int color = 0xFFFF0000;
                           // signintext.setText("Enter your credentials correctly..");
                            //signintext.setTextColor(color);
                            mprogress1.dismiss();
                        }

                    }
                });

            }


        }



    private void Checkuser() {
        if (mauthe.getCurrentUser() != null)
        {
            final String userid = mauthe.getCurrentUser().getUid();
            mfirebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(userid)) {
                        Intent RegisterIntent = new Intent(LoginActivity.this, Booking_info.class);
                        RegisterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(RegisterIntent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, Booking_info.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // signinbutton.setError("You need fill details correctly");
                }
            });
        }
    }
}