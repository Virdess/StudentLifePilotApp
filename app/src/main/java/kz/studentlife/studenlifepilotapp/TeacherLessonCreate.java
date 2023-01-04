package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import kz.studentlife.studenlifepilotapp.Group.GroupHTTP;
import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.TimeTable.Lesson.LessonHTTP;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableCreateHTTP;

public class TeacherLessonCreate extends AppCompatActivity {
    Spinner spinnerGroup, spinnerLesson, spinnerDay;
    TextView inputTimeStart, inputTimeEnd;
    ImageView addGroupImg, addLessonImg;
    Button lessonCreateBtn;
    FloatingActionButton turnBackBtnTLC;
    String groupName;
    String lessonName;
    String dayToday;
    GroupHTTP groupHTTP = new GroupHTTP();
    TimeTableCreateHTTP timeTableCreateHTTP = new TimeTableCreateHTTP();
    LessonHTTP lessonHTTP = new LessonHTTP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lesson_create);
        spinnerGroup = findViewById(R.id.spinnerGroup);
        spinnerLesson = findViewById(R.id.spinnerLesson);
        inputTimeStart = findViewById(R.id.inputTimeStart);
        inputTimeEnd = findViewById(R.id.inputTimeEnd);
        lessonCreateBtn = findViewById(R.id.lessonCreateBtn);
        turnBackBtnTLC = findViewById(R.id.turnBackBtnTLC);
        addGroupImg = findViewById(R.id.addGroupImg);
        addLessonImg = findViewById(R.id.addLessonImg);
        spinnerDay = findViewById(R.id.spinnerDay);

        List<String> groupsArray = new ArrayList<String>();
        List<String> lessonArray = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, groupsArray);
        ArrayAdapter<String> adapterLesson = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lessonArray);

        String[] arraySpinnerDay = new String[] {
                "MON",
                "TUE",
                "WED",
                "THU",
                "FRI",
                "SAT",
                "SUN"
        };

        groupHTTP.GroupsInSpinner(spinnerGroup, groupsArray, adapter, this, "groups");
        groupHTTP.GroupsInSpinner(spinnerLesson, lessonArray, adapterLesson, this, "lesson");

        inputTimeStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2){
                    System.out.println(inputTimeStart.getText());
                    String text = inputTimeStart.getText().toString();
                    inputTimeStart.setText(text + ":");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputTimeEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2){
                    System.out.println(inputTimeEnd.getText());
                    String text = inputTimeEnd.getText().toString();
                    inputTimeEnd.setText(text + ":");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lessonCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName = spinnerGroup.getSelectedItem().toString();
                lessonName = spinnerLesson.getSelectedItem().toString();
                dayToday = spinnerDay.getSelectedItem().toString();
                String timeStart = inputTimeStart.getText().toString() + ":00";
                String timeEnd = inputTimeEnd.getText().toString() + ":00";
                System.out.println(groupName + "\n" + lessonName+ "\n" + timeStart + "\n" + timeEnd + "\n" + dayToday);
                JSONObject timetableJSON = new JSONObject();

                try {
                    timetableJSON.put("timeStart",timeStart);
                    timetableJSON.put("timeEnd",timeEnd);
                    timetableJSON.put("day",dayToday);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lessonHTTP.GetLessonID(lessonName, timetableJSON, TeacherLessonCreate.this, groupName);
            }
        });

        turnBackBtnTLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addGroupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.AddGroup");
                startActivity(intent);
                finish();
            }
        });

        addLessonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.AddLesson");
                startActivity(intent);
                finish();
            }
        });



        ArrayAdapter<String> spinnerDayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerDay);
        spinnerDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(spinnerDayAdapter);








    }

}