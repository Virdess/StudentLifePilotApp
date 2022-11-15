package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kz.studentlife.studenlifepilotapp.UserHTTP.UserHTTP;

public class Login extends AppCompatActivity {
    Button startRegister, loginButton;
    UserHTTP userHttp = new UserHTTP();
    TextView loginInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startRegister = findViewById(R.id.startRegister);
        loginButton = findViewById(R.id.loginButton);
        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.passwordInput);

        startRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.RegisterPage1");
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userHttp.sendJsonPostRequest(Login.this, "/login", loginInput.getText().toString(), passwordInput.getText().toString());
                System.out.println(loginInput.getText().toString() + passwordInput.getText().toString());
            }
        });
    }
}