package kz.studentlife.studenlifepilotapp.TimeTable.Lesson;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.TimeTable.TimeTableCreateHTTP;

public class LessonHTTP {
    public String gotLessonID;
    TimeTableCreateHTTP timeTableCreateHTTP = new TimeTableCreateHTTP();
    public void CreateForTimetableHTTP(String code, String domain, Context context) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(context);



        JSONObject requestBodyJSON = new JSONObject();
        switch (domain){
            case "lesson":
                requestBodyJSON.put("lessonName",code);
                break;
            case "group_create":
                requestBodyJSON.put("name",code);
                break;
        }

        // Enter the correct url for your api service site
        String url = "http://188.130.234.67:8081/api/v1/" + domain;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBodyJSON,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public void GetLessonID(String lessonName, JSONObject timetableJSON, Context context, String groupName){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest lessonIdGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/lesson",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            for (int i = 0; jsonObject.length() > i; i++){
                                byte bytes[] = jsonObject.optJSONObject(i).getString("lessonName").getBytes("ISO-8859-1");
                                String lessonNameDecoded = new String(bytes, "UTF-8");
                                if (lessonNameDecoded.equals(lessonName)){
                                    System.out.println(jsonObject.optJSONObject(i).getString("id") + lessonName + "!!!!!!");
                                    gotLessonID = jsonObject.optJSONObject(i).getString("id");
                                    timetableJSON.put("lesson", gotLessonID);
                                    timeTableCreateHTTP.CreateTimetableHTTP(timetableJSON, context, groupName);
                                }
                            }
                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                System.out.println(error.toString());
            }
        });

        queue.add(lessonIdGet);
    }
}
