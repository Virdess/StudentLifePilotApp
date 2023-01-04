package kz.studentlife.studenlifepilotapp.TimeTable;

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

import kz.studentlife.studenlifepilotapp.Group.GroupHTTP;

public class TimeTableCreateHTTP{
    GroupHTTP groupHTTP = new GroupHTTP();
    public void CreateTimetableHTTP(JSONObject requestBodyJSON, Context context, String groupName) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        // Enter the correct url for your api service site
        String url = "http://188.130.234.67:8081/api/v1/timetable";
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
        StringRequest lessonIdGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/timetable/all",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; jsonArray.length() > i; i++){
                                jsonArray.optJSONObject(i).getString("id");

                                if (jsonArray.optJSONObject(i).getString("timeStart").equals(requestBodyJSON.getString("timeStart")) && jsonArray.optJSONObject(i).getString("timeEnd").equals(requestBodyJSON.getString("timeEnd")) && jsonArray.optJSONObject(i).getString("day").equals(requestBodyJSON.getString("day")) && jsonArray.optJSONObject(i).getString("lesson").equals(requestBodyJSON.getString("lesson"))){
                                    System.out.println(jsonArray.optJSONObject(i).getString("id") + " GOT TIMETABLE ID");
                                    JSONObject groupLessonCreate = new JSONObject();
                                    groupLessonCreate.put("timetableID", jsonArray.optJSONObject(i).getString("id"));
                                    groupHTTP.GetGroupIDHTTP(context, groupLessonCreate, groupName);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                System.out.println(error.toString());
            }
        });

        requestQueue.add(lessonIdGet);
    }
}
