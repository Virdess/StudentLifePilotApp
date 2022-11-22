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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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


    JWTDecode jwtDecode = new JWTDecode();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ahivementsBtn = view.findViewById(R.id.ahivementsBtn), aPerformanceBtn = view.findViewById(R.id.aPerformanceBtn);

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
        userHello = getView().findViewById(R.id.userHello);
        String username;
        try {
            username = new JSONObject(jwtDecode.payload).getString("sub");
            userHello.setText("Здравствуй, " + username);
            getTimetableHTTP(username, mContext, view);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void getTimetableHTTP(String username, Context context, View view){
        lessonList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.4:8081/api/v1/user/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getID = new JSONObject(response);
                            userid = getID.getString("id");
                            JSONArray lessonsPut = new JSONArray();
                            StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.4:8081/api/v1/user_groups_get/",
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
                                                StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.4:8081/api/v1/user_groups_get/",
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
                                                                    StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.4:8081/api/v1/group_lesson_get",
                                                                            new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {
                                                                                    JSONObject lessons = new JSONObject();

                                                                                    try {
                                                                                        String groupID = "";
                                                                                        JSONArray timetableList = new JSONArray(response);
                                                                                        for (int i = 0; timetableList.length() > i; i++){
                                                                                            if (timetableList.optJSONObject(i).getString("groupID").equals(finalTemp)){
                                                                                                groupID = timetableList.optJSONObject(i).getString("timetableID");
                                                                                                String finalGroupID = groupID;
                                                                                                StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.4:8081/api/v1/timetable/" + groupID,
                                                                                                        new Response.Listener<String>() {
                                                                                                            @Override
                                                                                                            public void onResponse(String response) {

                                                                                                                try {
                                                                                                                    JSONObject timetableList = new JSONObject(response);

                                                                                                                    StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.4:8081/api/v1/lesson/" + timetableList.getString("lesson"),
                                                                                                                            new Response.Listener<String>() {
                                                                                                                                @Override
                                                                                                                                public void onResponse(String response) {
                                                                                                                                    try {
                                                                                                                                        JSONObject resp = new JSONObject(response);
                                                                                                                                        JSONObject temporal = new JSONObject();
                                                                                                                                        byte decodeByte[] = resp.getString("lessonName").getBytes("ISO-8859-1");
                                                                                                                                        String lesson = new String(decodeByte, "UTF-8");
                                                                                                                                        String timeStart = timetableList.getString("timeStart");
                                                                                                                                        String timeEnd = timetableList.getString("timeEnd");
                                                                                                                                        String day = timetableList.getString("day");
                                                                                                                                        lessons.put("lessonName",lesson);
                                                                                                                                        lessons.put("timeStart", timeStart);
                                                                                                                                        lessons.put("timeEnd", timeEnd);
                                                                                                                                        lessons.put("day", day);
                                                                                                                                        lessonsPut.put(lessons.toString());
                                                                                                                                        System.out.println(lessons + "_____LESSONS  ");
                                                                                                                                        System.out.println(lessonsPut + "_____LESSONS!!!!");
                                                                                                                                        sortLessons(lessonsPut);
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

    private void sortLessons(JSONArray jsonArr) {

        try {
            System.out.println(jsonArr + "____JSONARR");
            JSONArray sortedJsonArray = new JSONArray();

            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < jsonArr.length(); i++) {
                //jsonValues.add(jsonArr.getJSONObject(i));
                System.out.println(jsonArr.getJSONArray(i) + "_______JSONVALUES");
            }
            Collections.sort( jsonValues, new Comparator<JSONObject>() {
                //You can change "Name" with "ID" if you want to sort by ID
                private static final String KEY_NAME = "Name";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = new String();
                    String valB = new String();

                    try {
                        valA = (String) a.get(KEY_NAME);
                        valB = (String) b.get(KEY_NAME);
                    }
                    catch (JSONException e) {
                        //do something
                    }

                    return valA.compareTo(valB);
                    //if you want to change the sort order, simply use the following:
                    //return -valA.compareTo(valB);
                }
            });

            for (int i = 0; i < jsonArr.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }
            System.out.println(sortedJsonArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String getJSON(String EncodedString) throws UnsupportedEncodingException{
        byte[] decodeByte = Base64.decode(EncodedString, Base64.URL_SAFE);
        return new String(decodeByte, StandardCharsets.UTF_8);
    }
}