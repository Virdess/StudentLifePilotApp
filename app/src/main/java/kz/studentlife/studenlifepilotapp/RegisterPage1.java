package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPage1 extends AppCompatActivity {

    Button continueReg;
    TextView regNameInput, regMidNameInput, regLastNameInput;
    public static JSONObject regData = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page1);

        continueReg = findViewById(R.id.continueReg);
        regNameInput = findViewById(R.id.regNameInput);
        regMidNameInput = findViewById(R.id.regMidNameInput);
        regLastNameInput = findViewById(R.id.regLastNameInput);



        //Получаем имя, фамилию, отчество и отправляем их в следующую Activity для того, чтобы позднее вызвав метод регистрации
        //поместить в тело запроса, сделано для разгрузки страницы регистрации
        continueReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    regData.put("firstName", regNameInput.getText().toString());
                    regData.put("midName", regMidNameInput.getText().toString());
                    regData.put("lastName", regLastNameInput.getText().toString());
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent("android.intent.action.RegisterPage2");
                startActivity(intent);
            }
        });
    }
}