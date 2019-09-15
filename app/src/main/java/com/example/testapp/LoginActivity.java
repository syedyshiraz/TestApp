package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    String mVerificationId,code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setTheme(getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark", true) ? R.style.AppTheme : R.style.LightTheme);
        setContentView(R.layout.activity_login);

        String phoneNumber = "+918548080255";

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        /*FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getApplicationContext(),"You've signed in successfully",Toast.LENGTH_LONG).show();
                            }
                        });*/


                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        mVerificationId=s;
                    }
                });

        Button button=(Button)findViewById(R.id.register);
        final EditText editText=(EditText) findViewById(R.id.phone);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code=editText.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,code);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(authResult.getUser()!=null)
                            Toast.makeText(getApplicationContext(),"Yay right otp",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(),"Nope try again bitch",Toast.LENGTH_LONG).show();


                    }
                });


            }
        });











    }
}