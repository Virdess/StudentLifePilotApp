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

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.TimeTable.GetTimetableHTTP;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableAdapter;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableModel;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeTableFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeTableFragment newInstance(String param1, String param2) {
        TimeTableFragment fragment = new TimeTableFragment();
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
        return inflater.inflate(R.layout.fragment_time_table, container, false);
    }
    RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    List<TimeTableModel>lessonList;
    TimeTableAdapter adapter;
    JWTDecode jwtDecode = new JWTDecode();
    GetTimetableHTTP getTimetableHTTP = new GetTimetableHTTP();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar rightNow = Calendar.getInstance();
        TextView dayNow = view.findViewById(R.id.dayNow);
        System.out.println(rightNow.toString());

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest timetable = new StringRequest(Request.Method.GET, "http://192.168.1.2:8081/api/v1/lesson/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            byte decodeByte[] = response.getBytes("ISO-8859-1");

                            lessonList.add(new TimeTableModel("", "12:10-13:40"));
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

        queue.add(timetable);
        initData(view);
    }



    private void initData(View view) {
        String getSub = "";

        try {
            getSub = new JSONObject(jwtDecode.payload).getString("sub");
            getTimetableHTTP.getTimetableHTTP(getSub, mContext, view);
            GetLessons(getSub, view);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GetLessons(String username, View view) {
        lessonList = new ArrayList<>();
        lessonList.add(new TimeTableModel("Русский", "12:10-13:40"));
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest user = new StringRequest(Request.Method.GET, "http://192.168.1.2:8081/api/v1/user/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String userID = new JSONObject(response).getString("id");
                            System.out.println(userID + " ---USERID");
                            StringRequest timetable = new StringRequest(Request.Method.GET, "http://192.168.1.2:8081/api/v1/group_lesson_get/",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                byte decodeByte[] = response.getBytes("ISO-8859-1");
                                                String lessons = new String(decodeByte, StandardCharsets.UTF_8);
                                                System.out.println(lessons);
                                                JSONArray getGroupsList = new JSONArray(lessons);
                                                System.out.println(getGroupsList.optJSONObject(0) + "------GETGROUPSLIST");
                                                for (int i = 0; getGroupsList.length() > i; i++){
                                                    System.out.println(getGroupsList.optJSONObject(i) + "_____GETGROUPSLISTOBJ");
                                                    lessonList.add(new TimeTableModel(getGroupsList.optJSONObject(i).getString("timetableID"), "12:10-13:40"));
                                                }
                                                initRecyclerView(view);
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener(){
                                @Override
                                public void onErrorResponse (VolleyError error){
                                    System.out.println(error);
                                }
                            });

                            queue.add(timetable);

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

        queue.add(user);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(View view) {
        recyclerview = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new TimeTableAdapter(lessonList);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}