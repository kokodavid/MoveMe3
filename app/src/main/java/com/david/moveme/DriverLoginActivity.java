package com.david.moveme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;

public class DriverLoginActivity extends AppCompatActivity {

    @BindView(R.id.email)  EditText email;
    @BindView(R.id.password)  EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
    }
}
