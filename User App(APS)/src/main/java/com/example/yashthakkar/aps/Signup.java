package com.example.yashthakkar.aps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private EditText emailinput,passinput,confirmpass,username,aadharcard;
    Button button;
    private DatabaseReference mdatabasee;
    private FirebaseAuth mauth;
    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mauth=FirebaseAuth.getInstance();
        mdatabasee= FirebaseDatabase.getInstance().getReference().child("users");
        emailinput=(EditText)findViewById(R.id.emailinput);
        passinput=(EditText)findViewById(R.id.passinput);
        confirmpass=(EditText)findViewById(R.id.confirmpass);
        username=(EditText)findViewById(R.id.username);
        button=(Button)findViewById(R.id.button);
        aadharcard=(EditText)findViewById(R.id.adharcard);
        mprogress=new ProgressDialog(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signupin();
            }
        });
    }

    public void Signupin(){

        final String name=username.getText().toString().trim();
        String email=emailinput.getText().toString().trim();
        String pass=passinput.getText().toString().trim();
        String confpass=confirmpass.getText().toString().trim();
        final String aadharcar=aadharcard.getText().toString().trim();
        if(name.isEmpty())
        {
            username.setError("username is required");
            username.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailinput.setError("Email is required");
            emailinput.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailinput.setError("Please enter a valid email");
            emailinput.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            passinput.setError("Password is required");
            passinput.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            passinput.setError("Minimum length of password should be 6");
            passinput.requestFocus();
            return;
        }
        if(confpass.isEmpty()){
            confirmpass.setError("This field is not be left empty");
            passinput.requestFocus();
            return;
        }
        if(!pass.equals(confpass)){
            confirmpass.setError("Password din't matched");
            passinput.requestFocus();
            return;
        }
        else {
            mprogress.setMessage("Saving your details");
            mprogress.show();

            mauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String user_id = mauth.getCurrentUser().getUid();
                        DatabaseReference currentuser = mdatabasee.child(user_id);
                        currentuser.child("name").setValue(name);
                        currentuser.child("Aadharcard").setValue(aadharcar);

                        mprogress.dismiss();
                        Intent intent = new Intent(Signup.this, Booking_info.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        mprogress.dismiss();
                        Toast.makeText(Signup.this, "Sorry cannot save your details please enter another emailid", Toast.LENGTH_LONG).show();
                    }


                }
            });
        }


    }
}
