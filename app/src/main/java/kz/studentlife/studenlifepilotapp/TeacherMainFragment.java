package kz.studentlife.studenlifepilotapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.TimeTable.GetTimetableHTTP;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableAdapter;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableAdapterTeacher;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableModel;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableModelTeacher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherMainFragment extends Fragment {

    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherMainFragment newInstance(String param1, String param2) {
        TeacherMainFragment fragment = new TeacherMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_main, container, false);
    }




    RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    List<TimeTableModel> lessonList;
    TimeTableAdapter adapter;
    JWTDecode jwtDecode = new JWTDecode();
    GetTimetableHTTP getTimetableHTTP = new GetTimetableHTTP();
    String weekDayName;
    public static String userid;
    TextView helloTeacher;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar rightNow = Calendar.getInstance();
        TextView dayNow = view.findViewById(R.id.dayNow);
        String dateToday = rightNow.get(Calendar.YEAR) + "." + rightNow.get(Calendar.MONTH) + "." + rightNow.get(Calendar.DAY_OF_MONTH);

        helloTeacher = getView().findViewById(R.id.helloTeacher);
        String username;
        try {
            username = new JSONObject(jwtDecode.payload).getString("sub");
            helloTeacher.setText("Здравствуйте, " + username);
            getTimetableHTTP(username, mContext, view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int dayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek){
            case 1:
                System.out.println("SUN");
                weekDayName = "SUN";
                break;
            case 2:
                System.out.println("MON");
                weekDayName = "MON";
                break;
            case 3:
                System.out.println("TUE");
                weekDayName = "TUE";
                break;
            case 4:
                System.out.println("WED");
                weekDayName = "WED";
                break;
            case 5:
                System.out.println("THU");
                weekDayName = "THU";
                break;
            case 6:
                System.out.println("FRI");
                weekDayName = "FRI";
                break;
            case 7:
                System.out.println("SAT");
                weekDayName = "SAT";
                break;
        }
        initData(view);

        FloatingActionButton createLesson = view.findViewById(R.id.createLesson);

        createLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.TeacherLessonCreate");
                startActivity(intent);
            }
        });
    }


    private void initData(View view) {
        String getSub = "";

        try {
            getSub = new JSONObject(jwtDecode.payload).getString("sub");
            getTimetableHTTP(getSub, mContext, view);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(View view) {
        recyclerview = view.findViewById(R.id.recyclerviewTeacher);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new TimeTableAdapter(lessonList);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    public void getTimetableHTTP(String username, Context context, View view){
        lessonList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest groupGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/user/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getID = new JSONObject(response);
                            userid = getID.getString("id");
                            StringRequest groupGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/user_groups_get/",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                String temp = "";
                                                JSONArray groupsList = new JSONArray(response);
                                                for (int i = 0; groupsList.length() > i; i++){
                                                    if (groupsList.optJSONObject(i).getString("userID").equals(userid)){
                                                        temp = groupsList.optJSONObject(i).getString("groupStudentID");
                                                    }
                                                }
                                                StringRequest groupGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/user_groups_get/",
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    String temp = "";
                                                                    JSONArray groupsList = new JSONArray(response);
                                                                    for (int i = 0; groupsList.length() > i; i++){
                                                                        if (groupsList.optJSONObject(i).getString("userID").equals(userid)){
                                                                            temp = groupsList.optJSONObject(i).getString("groupStudentID");
                                                                        }
                                                                    }


                                                                    String finalTemp = temp;
                                                                    StringRequest groupGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/group_lesson_get",
                                                                            new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {

                                                                                    try {
                                                                                        String groupID = "";
                                                                                        JSONArray timetableList = new JSONArray(response);
                                                                                        for (int i = 0; timetableList.length() > i; i++){
                                                                                            if (timetableList.optJSONObject(i).getString("groupID").equals(finalTemp)){
                                                                                                groupID = timetableList.optJSONObject(i).getString("timetableID");
                                                                                                String finalGroupID = groupID;
                                                                                                StringRequest groupGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/timetable/" + groupID,
                                                                                                        new Response.Listener<String>() {
                                                                                                            @Override
                                                                                                            public void onResponse(String response) {

                                                                                                                try {
                                                                                                                    JSONObject timetableList = new JSONObject(response);

                                                                                                                    StringRequest groupGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/lesson/" + timetableList.getString("lesson"),
                                                                                                                            new Response.Listener<String>() {
                                                                                                                                @Override
                                                                                                                                public void onResponse(String response) {
                                                                                                                                    try {
                                                                                                                                        JSONObject resp = new JSONObject(response);
                                                                                                                                        if (timetableList.getString("day").equals(weekDayName)){
                                                                                                                                            byte decodeByte[] = resp.getString("lessonName").getBytes("ISO-8859-1");
                                                                                                                                            String lesson = new String(decodeByte, "UTF-8");
                                                                                                                                            String timeStart = timetableList.getString("timeStart");
                                                                                                                                            String timeEnd = timetableList.getString("timeEnd");
                                                                                                                                            String day = timetableList.getString("day");
                                                                                                                                            String lessonName = lesson;
                                                                                                                                            String splitted = StringUtils.substring(timeStart, 0, timeStart.length() - 3) + " - " + StringUtils.substring(timeEnd, 0, timeEnd.length() - 3);
                                                                                                                                            lessonList.add(new TimeTableModel(lessonName, splitted));
                                                                                                                                            Collections.sort(lessonList, new Comparator<TimeTableModel>() {
                                                                                                                                                @Override
                                                                                                                                                public int compare(TimeTableModel o1, TimeTableModel o2) {
                                                                                                                                                    return o1.getLessonTime().compareToIgnoreCase(o2.getLessonTime());
                                                                                                                                                }
                                                                                                                                            });

                                                                                                                                            System.out.println(lessonList + "______SORTED?_____"  + weekDayName);
                                                                                                                                            initRecyclerView(view);
                                                                                                                                        }

                                                                                                                                    } catch (UnsupportedEncodingException e) {
                                                                                                                                        e.printStackTrace();
                                                                                                                                    } catch (JSONException e) {
                                                                                                                                        e.printStackTrace();
                                                                                                                                    }

                                                                                                                                }
                                                                                                                            }, new Response.ErrorListener(){
                                                                                                                        @Override
                                                                                                                        public void onErrorResponse (VolleyError error){
                                                                                                                            System.out.println(error);
                                                                                                                        }
                                                                                                                    });
                                                                                                                    queue.add(groupGet);
                                                                                                                } catch (JSONException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                }


                                                                                                            }
                                                                                                        }, new Response.ErrorListener(){
                                                                                                    @Override
                                                                                                    public void onErrorResponse (VolleyError error){
                                                                                                        System.out.println(error);
                                                                                                    }
                                                                                                });
                                                                                                queue.add(groupGet);
                                                                                            }
                                                                                        }





                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                    }


                                                                                }
                                                                            }, new Response.ErrorListener(){
                                                                        @Override
                                                                        public void onErrorResponse (VolleyError error){
                                                                            System.out.println(error);
                                                                        }
                                                                    });

                                                                    queue.add(groupGet);





                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }
                                                        }, new Response.ErrorListener(){
                                                    @Override
                                                    public void onErrorResponse (VolleyError error){
                                                        System.out.println(error);
                                                    }
                                                });

                                                queue.add(groupGet);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener(){
                                @Override
                                public void onErrorResponse (VolleyError error){
                                    System.out.println(error);
                                }
                            });

                            queue.add(groupGet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                System.out.println(error);
            }
        });

        queue.add(groupGet);
    }
}