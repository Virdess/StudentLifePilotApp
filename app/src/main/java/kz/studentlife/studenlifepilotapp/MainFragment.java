package kz.studentlife.studenlifepilotapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    List<TimeTableModel> lessonList;
    public static String userid;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    TextView userHello;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    String weekDayName;
    JWTDecode jwtDecode = new JWTDecode();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ahivementsBtn = view.findViewById(R.id.ahivementsBtn), aPerformanceBtn = view.findViewById(R.id.performanceBtn);

        Calendar rightNow = Calendar.getInstance();
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




        ahivementsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.NotCreatedActivity");
                startActivity(intent);
            }
        });

        aPerformanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.NotCreatedActivity");
                startActivity(intent);
            }
        });
        String username;
        try {
            username = new JSONObject(jwtDecode.payload).getString("sub");
            getTimetableHTTP(username, mContext, view);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

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

                                                                                                                                        TextView lessonName = view.findViewById(R.id.lessonName), lessonTime = view.findViewById(R.id.lessonTime);
                                                                                                                                        JSONObject resp = new JSONObject(response);
                                                                                                                                        if (timetableList.getString("day").equals(weekDayName)){
                                                                                                                                            int timestart = Integer.parseInt(StringUtils.substring(timetableList.getString("timeStart"), 0, timetableList.getString("timeStart").length() - 6));
                                                                                                                                            System.out.println(timestart);
                                                                                                                                            Calendar rightNow = Calendar.getInstance();
                                                                                                                                            int timeNow = rightNow.get(Calendar.HOUR_OF_DAY);
                                                                                                                                            ProgressBar progressBar3 = view.findViewById(R.id.progressBar3);
                                                                                                                                            progressBar3.setVisibility(View.GONE);
                                                                                                                                            int newTime = timeNow+1;
                                                                                                                                            System.out.println(newTime + " ____NEWTIME------" + timeNow);
                                                                                                                                            if ((timeNow < timestart) && (timestart <= newTime)){
                                                                                                                                                byte decodeByte[] = resp.getString("lessonName").getBytes("ISO-8859-1");
                                                                                                                                                String lesson = new String(decodeByte, "UTF-8");
                                                                                                                                                System.out.println(lesson);
                                                                                                                                                lessonName.setText(lesson);
                                                                                                                                                String timeStart = timetableList.getString("timeStart");
                                                                                                                                                String timeEnd = timetableList.getString("timeEnd");
                                                                                                                                                String splitted = StringUtils.substring(timeStart, 0, timeStart.length() - 3) + " - " + StringUtils.substring(timeEnd, 0, timeEnd.length() - 3);
                                                                                                                                                lessonTime.setText(splitted);
                                                                                                                                            }
                                                                                                                                            else {
                                                                                                                                                lessonName.setText("Пар на сегодня больше нет");
                                                                                                                                                lessonTime.setText("- - - -");
                                                                                                                                            }
                                                                                                                                        }

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

    public static String getJSON(String EncodedString) throws UnsupportedEncodingException{
        byte[] decodeByte = Base64.decode(EncodedString, Base64.URL_SAFE);
        return new String(decodeByte, StandardCharsets.UTF_8);
    }
}