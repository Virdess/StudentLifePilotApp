package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

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

import kz.studentlife.studenlifepilotapp.TimeTable.Lesson.LessonHTTP;

public class AddLesson extends AppCompatActivity {
    FloatingActionButton turnBackBtnAL;
    TextView lessonNameCreateInput;
    Button lessonNameCreateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        turnBackBtnAL = findViewById(R.id.turnBackBtnAL);
        lessonNameCreateBtn = findViewById(R.id.lessonNameCreateBtn);
        lessonNameCreateInput = findViewById(R.id.lessonNameCreateInput);
        LessonHTTP lessonHTTP = new LessonHTTP();



        turnBackBtnAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent("android.intent.action.TeacherLessonCreate");
                startActivity(intent);
            }
        });


        lessonNameCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lessonName = lessonNameCreateInput.getText().toString();
                try {
                    lessonHTTP.CreateForTimetableHTTP(lessonName, "lesson", AddLesson.this);
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