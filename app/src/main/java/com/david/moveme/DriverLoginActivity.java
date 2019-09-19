package com.david.moveme;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.muddzdev.styleabletoast.StyleableToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriverLoginActivity extends AppCompatActivity {

    @BindView(R.id.email)  EditText memail;
    @BindView(R.id.password)  EditText mpassword;
    @BindView(R.id.login)  Button mlogin;
    @BindView(R.id.registration)  Button mregister;


    private FirebaseAuth mAuth;
   private FirebaseAuth.AuthStateListener firebaseAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(DriverLoginActivity.this,DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = memail.getText().toString();
                final String password = mpassword.getText().toString();
                mAuth = FirebaseAuth.getInstance();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            StyleableToast.makeText(DriverLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }else{
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
                            current_user_db.setValue(true);

                        }
                    }
                });
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = memail.getText().toString();
                final String password = mpassword.getText().toString();
                mAuth = FirebaseAuth.getInstance();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        StyleableToast.makeText(DriverLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
       mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
