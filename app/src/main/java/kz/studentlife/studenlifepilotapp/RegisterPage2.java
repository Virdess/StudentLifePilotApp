package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.JWT.TokenManager;
import kz.studentlife.studenlifepilotapp.UserHTTP.UserHTTP;

public class RegisterPage2 extends AppCompatActivity {
    public static JSONObject regData = new JSONObject();
    public static String userID;

    Button continueReg2;
    TextView regUsernameInput,regPasswordInput,regPhoneInput,reqMailInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page2);
        continueReg2 = findViewById(R.id.continueReg2);
        regUsernameInput = findViewById(R.id.regUsernameInput);
        regPasswordInput = findViewById(R.id.regPasswordInput);
        regPhoneInput = findViewById(R.id.regPhoneInput);
        reqMailInput = findViewById(R.id.reqMailInput);
        regData = RegisterPage1.regData;
        System.out.println(regData);
        RegisterPage1 registerPage1 = new RegisterPage1();

        //Получаем логин, пароль. телефон, почту и отправляем их в следующую Activity (RegisterPage3)для того, чтобы позднее вызвав метод регистрации
        //поместить в тело запроса, сделано для разгрузки страницы регистрации
        continueReg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    regData.put("username", regUsernameInput.getText().toString());
                    regData.put("password", regPasswordInput.getText().toString());
                    regData.put("phone", regPhoneInput.getText().toString());
                    regData.put("email", reqMailInput.getText().toString());
                    regData.put("role", "ROLE_STUDENT");
                    UserHTTP userHTTP = new UserHTTP();
                    userHTTP.CreateUserHTTP(regData, RegisterPage2.this);
                    registerPage1.finish();
                    userHTTP.GetUserIDHTTP(regData.getString("username"), RegisterPage2.this);
//                    userID = userHTTP.userid;
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent("android.intent.action.RegisterPage3");
                startActivity(intent);
            }
        });
    }


}