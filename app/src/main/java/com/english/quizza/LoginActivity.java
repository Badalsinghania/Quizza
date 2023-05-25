package com.english.quizza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.english.quizza.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
 private TextView forgotpassword;
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    private EditText emailbox;
    private EditText passwordbox;
    private Button loginbox;
    boolean passvisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        forgotpassword = findViewById(R.id.forgotpassword);
        passwordbox = findViewById(R.id.passwordbox);
        passwordbox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX() >= passwordbox.getRight()-passwordbox.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = passwordbox.getSelectionEnd();
                        if (passvisible) {
                            passwordbox.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            passwordbox.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordbox.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible = false;
                        } else {
                            passwordbox.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            passwordbox.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordbox.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible = true;
                        }
                        passwordbox.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        auth = FirebaseAuth.getInstance();

       dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in...");
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


        binding.loginbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass;
                email = binding.emailbox.getText().toString();
                pass = binding.passwordbox.getText().toString();


             //   dialog.show();

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

            });
            }
        });

        binding.createbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        emailbox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);
        loginbox = findViewById(R.id.loginbox);
        emailbox.addTextChangedListener(loginTextWatcher);
        passwordbox.addTextChangedListener(loginTextWatcher);
    }
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String email = emailbox.getText().toString().trim();
            String pass = passwordbox.getText().toString().trim();

            loginbox.setEnabled(!email.isEmpty() && !pass.isEmpty());
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}