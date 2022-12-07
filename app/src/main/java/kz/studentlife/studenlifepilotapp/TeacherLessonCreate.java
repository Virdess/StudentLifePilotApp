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

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;

public class TeacherLessonCreate extends AppCompatActivity {
    Spinner spinnerGroup, spinnerLesson, spinnerDay;
    TextView inputTimeStart, inputTimeEnd;
    ImageView addGroupImg, addLessonImg;
    Button lessonCreateBtn;
    FloatingActionButton turnBackBtnTLC;
    String groupName;
    String lessonName;

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
                "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
        };

        GroupsInSpinner(spinnerGroup, groupsArray, adapter);
        LessonsInSpinner(spinnerLesson, lessonArray, adapterLesson);


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
                String timeStart = inputTimeStart.getText().toString() + ":00";
                String timeEnd = inputTimeEnd.getText().toString() + ":00";
                System.out.println(groupName + "\n" + lessonName+ "\n" + timeStart + "\n" + timeEnd);
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
            }
        });

        addLessonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.AddLesson");
                startActivity(intent);
            }
        });



        ArrayAdapter<String> spinnerDayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerDay);
        spinnerDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(spinnerDayAdapter);
    }




    private void CreateTimetable(){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//
//
//        JWTDecode jwtDecode = new JWTDecode();
//
//        // Enter the correct url for your api service site
//        String url = "http://192.168.1.2:8081/api/v1/signup_prof";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, regData,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
    }





    //Getting items for spinners (Groups, lessons) to create timetable
    private void GroupsInSpinner(Spinner spinnerGroup, List<String> groupsArray, ArrayAdapter<String> adapter){

        String baseUrl = "http://188.130.234.67:8081/api/v1/groups";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray resp = new JSONArray(response);
                            for (int i = 0; resp.length() > i; i++){
                                byte bytes[] = resp.optJSONObject(i).getString("name").getBytes("ISO-8859-1");
                                String groupsNamesDecoded = new String(bytes, "UTF-8");
                                System.out.println(groupsNamesDecoded);
                                groupsArray.add(groupsNamesDecoded);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerGroup.setAdapter(adapter);
                            }

                            //resp = new JSONArray(groupsNamesDecoded);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                System.out.println(error);
            }
        });
        queue.add(stringRequest);
    }

    private void LessonsInSpinner(Spinner spinnerGroup, List<String> lessonArray, ArrayAdapter<String> adapterLesson){

        String baseUrl = "http://188.130.234.67:8081/api/v1/lesson";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray resp = new JSONArray(response);
                            for (int i = 0; resp.length() > i; i++){
                                byte bytes[] = resp.optJSONObject(i).getString("lessonName").getBytes("ISO-8859-1");
                                String groupsNamesDecoded = new String(bytes, "UTF-8");
                                System.out.println(groupsNamesDecoded);
                                lessonArray.add(groupsNamesDecoded);
                                adapterLesson.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerGroup.setAdapter(adapterLesson);
                            }

                            //resp = new JSONArray(groupsNamesDecoded);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                System.out.println(error);
            }
        });
        queue.add(stringRequest);
    }



}