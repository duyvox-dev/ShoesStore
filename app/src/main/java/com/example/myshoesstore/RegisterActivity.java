package com.example.myshoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshoesstore.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText edtNameRegister, edtEmailRegister, edtPasswordRegister;
    Button btnSignUp;
    TextView txtRegister;
    FirebaseAuth auth;
    FirebaseDatabase database;

    ProgressBar pbRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        edtNameRegister = findViewById(R.id.editTextNameRegister);
        edtEmailRegister = findViewById(R.id.editTextEmailRegister);
        edtPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        btnSignUp = findViewById(R.id.buttonSignUp);
        txtRegister = findViewById(R.id.textViewRegister);
        pbRegister = findViewById(R.id.progressBarRegister);

        pbRegister.setVisibility(View.GONE);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                pbRegister.setVisibility(View.VISIBLE);
            }
        });


    }
    private void createUser(){
        String userName = edtNameRegister.getText().toString();
        String userEmail = edtEmailRegister.getText().toString();
        String userPassword = edtPasswordRegister.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length()<6){
            Toast.makeText(this, "Mật kẩu cần có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            User user = new User(userName, userEmail, userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            pbRegister.setVisibility(View.GONE);


                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            pbRegister.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Lỗi: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}