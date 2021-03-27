package com.example.easygo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easygo.Passengers_UI.UserHome;
import com.example.easygo.Ticket_Booking_Master.AdminHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText Email,Password;
    Button Login;
    TextView Registration,forgetPass;
    DatabaseReference userRef;
    FirebaseAuth mAuth;
    ImageView ImageView;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if (user!=null){
            if(SplashScreen.currentUser!=null){
                if (SplashScreen.currentUser.isAdmin){
                    startActivity(new Intent(getApplicationContext(), AdminHome.class));

                }else{
                    startActivity(new Intent(getApplicationContext(), UserHome.class));
                }
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login =findViewById(R.id.Login);
        Email=findViewById(R.id.Email);
        Password=findViewById(R.id.Password);
        Registration=findViewById(R.id.Registration );
        forgetPass=findViewById(R.id.forgetPass);
        mAuth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference("Users");
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecoveryPasswordDialoug();
            }
        });
        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=Email.getText().toString();
                String password=Password.getText().toString();
                try {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uid=mAuth.getCurrentUser().getUid();
                                userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        try {
                                            UserModel userModel=snapshot.getValue(UserModel.class);
                                            if (userModel.isAdmin()){
                                                startActivity(new Intent(getApplicationContext(), AdminHome.class));
                                            }else {
                                                startActivity(new Intent(getApplicationContext(),UserHome.class));
                                            }

                                        }catch (Exception e){


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(Login.this, "Confirm Cancellation ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_pass_btn){

            if(Password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_eye);

                //Show Password
                Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_eye_off);

                //Hide Password
                Password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void showRecoveryPasswordDialoug() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText etEmail=new EditText(this);
        etEmail.setHint("Email");
        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etEmail.setMinEms(16);
        linearLayout.addView(etEmail);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email=etEmail.getText().toString().trim();
                beingRecovery(email);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.create().show();
    }

    private void beingRecovery(String email) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Login.this,"Email Sent",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(Login.this,"Failed...",Toast.LENGTH_SHORT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }













}

