package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.TimeTable.Lesson.LessonHTTP;

public class AddGroup extends AppCompatActivity {
    FloatingActionButton turnBackBtnAG;
    TextView groupCodeCreateInput;
    Button groupAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        turnBackBtnAG = findViewById(R.id.turnBackBtnAG);
        groupAddBtn = findViewById(R.id.groupAddBtn);
        groupCodeCreateInput = findViewById(R.id.groupCodeCreateInput);
        LessonHTTP lessonHTTP = new LessonHTTP();

        turnBackBtnAG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent("android.intent.action.TeacherLessonCreate");
                startActivity(intent);
            }
        });

        groupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupCode = groupCodeCreateInput.getText().toString();
                System.out.println(groupCode);
                try {
                    lessonHTTP.CreateForTimetableHTTP(groupCode, "group_create", AddGroup.this);
                    Intent intent = new Intent("android.intent.action.TeacherLessonCreate");
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}