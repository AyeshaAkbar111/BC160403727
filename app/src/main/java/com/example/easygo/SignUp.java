package com.example.easygo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santalu.maskara.widget.MaskEditText;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuth;
    EditText UserName,Email,password;
    MaskEditText PhoneNo;
    Button SignUp;
    TextView LogIn;
    String userName,email,Password,phoneNo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        LogIn=findViewById(R.id.LogIn);
        UserName=findViewById(R.id.UserName);
        Email=findViewById(R.id.Email);
        password=findViewById(R.id.password);
        PhoneNo=findViewById(R.id.PhoneNo);
        SignUp=findViewById(R.id.SignUp);
        SignUp.setOnClickListener(this);
        LogIn.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userRef=firebaseDatabase.getReference("Users");
    }

   //database
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SignUp:
                userName=UserName.getText().toString().trim();
                email=Email.getText().toString().trim();
                Password=password.getText().toString().trim();
                phoneNo=PhoneNo.getText().toString().trim();
                if(userName.isEmpty())
                {
                    UserName.setError("Field Required");
                    return;
                }
                if (userName.contains(".")||userName.contains("#")||userName.contains("$")||userName.contains("[")||userName.contains("]"))
                {
                    Toast.makeText(SignUp.this, "UserName Is Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.isEmpty())
                {
                    Email.setError("Field Required");
                    return;
                }
                if(Password.isEmpty())
                {
                    password.setError("Field Required");
                    return;
                }
                if(phoneNo.isEmpty())
                {
                    PhoneNo.setError("Field Required");
                    return;
                }
                   try {
                       mAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                              if (task.isSuccessful()){
                                  FirebaseUser firebaseUser=mAuth.getCurrentUser();
                                  UserModel userModel=new UserModel(firebaseUser.getUid(),userName,email,phoneNo,false);
                                   userRef.child(firebaseUser.getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()){
                                               Toast.makeText(SignUp.this, userName+"Successfully SignUp", Toast.LENGTH_SHORT).show();
                                               startActivity(new Intent(getApplicationContext(),Login.class));

                                           }else{
                                               Toast.makeText(SignUp.this, "Fail", Toast.LENGTH_SHORT).show();

                                           }
                                       }
                                   });
                              }
                           }
                       });

                   }catch (Exception e){

                   }

                break;
            case R.id.LogIn:
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;

        }

    }

    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_pass_btn){

            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_eye);

                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_eye_off);

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }

    }
    

}
