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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmailLogin, edtPasswordLogin;
    Button btnLogin;
    TextView txtLogin;

    FirebaseAuth auth;

    ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        edtEmailLogin = findViewById(R.id.editTextEmailLogin);
        edtPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        btnLogin = findViewById(R.id.buttonSignIn);
        txtLogin = findViewById(R.id.textViewLogin);
        pbLogin = findViewById(R.id.progressBarLogin);

        pbLogin.setVisibility(View.GONE);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                pbLogin.setVisibility(View.VISIBLE);

            }
        });
    }
    public void loginUser() {
        String userEmail = edtEmailLogin.getText().toString();
        String userPassword = edtPasswordLogin.getText().toString();


        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            pbLogin.setVisibility(View.GONE);

                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            pbLogin.setVisibility(View.GONE);

                            Toast.makeText(LoginActivity.this, "Lỗi "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}