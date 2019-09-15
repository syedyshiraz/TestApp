package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    String mVerificationId,code;
    EditText phone,otp;
    MaterialButton button;
    String sOtp,sPhone;
    Context context=this;
    Activity activity=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setTheme(getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark", true) ? R.style.AppTheme : R.style.LightTheme);
        setContentView(R.layout.activity_login);

        phone=(EditText) findViewById(R.id.phoneN);
        otp=(EditText) findViewById(R.id.otp);
        button=findViewById(R.id.register);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPhone = phone.getText().toString();
                if (sPhone.isEmpty() || sPhone.length() < 10)
                    new AlertDialog.Builder(context).setTitle("Try again!").setMessage("Please enter a valid phone number").setIcon(R.drawable.ic_error).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                else if(otp.getVisibility()==View.GONE){
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+sPhone,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            activity,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getApplicationContext(),"You've signed in successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                                activity.finish();
                            }
                        });

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
                    otp.setVisibility(View.VISIBLE);
                    button.setText("Verify and Register");
                }
                else {
                    sOtp=otp.getText().toString();
                    if (sOtp.isEmpty() || sOtp.length() < 6)
                        new AlertDialog.Builder(context).setTitle("Try again!").setMessage("Please enter a valid Otp").setIcon(R.drawable.ic_error).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    else {
                        registerwithphone();
                    }

                }
            }});
        }







    public void registerwithphone(){

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,sOtp);
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
}