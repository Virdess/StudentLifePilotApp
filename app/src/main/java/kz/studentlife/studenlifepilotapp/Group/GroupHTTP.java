package kz.studentlife.studenlifepilotapp.Group;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.List;

public class GroupHTTP {
    //Getting items for spinners (Groups, lessons) to create timetable
    public void GroupsInSpinner(Spinner spinnerGroup, List<String> groupsArray, ArrayAdapter<String> adapter, Context context, String domain){

        String baseUrl = "http://188.130.234.67:8081/api/v1/" + domain;

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray resp = new JSONArray(response);
                            for (int i = 0; resp.length() > i; i++){
                                if (domain.equals("groups")){

                                    byte bytes[] = resp.optJSONObject(i).getString("name").getBytes("ISO-8859-1");
                                    String groupsNamesDecoded = new String(bytes, "UTF-8");
                                    System.out.println(groupsNamesDecoded);
                                    groupsArray.add(groupsNamesDecoded);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerGroup.setAdapter(adapter);
                                }
                                else {
                                    byte bytes[] = resp.optJSONObject(i).getString("lessonName").getBytes("ISO-8859-1");
                                    String groupsNamesDecoded = new String(bytes, "UTF-8");
                                    System.out.println(groupsNamesDecoded);
                                    groupsArray.add(groupsNamesDecoded);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerGroup.setAdapter(adapter);
                                }
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

    public void GetGroupIDHTTP(Context context, JSONObject requestBodyJSON, String groupName){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest groupIdGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/group_get/" + groupName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            byte bytes[] = jsonObject.getString("name").getBytes("ISO-8859-1");
                            String groupNameDecoded = new String(bytes, "UTF-8");
                            System.out.println(groupNameDecoded);
                            if (groupNameDecoded.equals(groupName)) {
                                System.out.println(jsonObject.getString("id"));
                                requestBodyJSON.put("groupID", jsonObject.getString("id"));
                                System.out.println(requestBodyJSON.toString() + "REQBODYJSON!!!!!!!");
                                GroupStudentLessonCreate(context, requestBodyJSON);
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

        queue.add(groupIdGet);
    }

    public void GroupStudentLessonCreate(Context context, JSONObject requestBodyJSON){
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        // Enter the correct url for your api service site
        String url = "http://188.130.234.67:8081/api/v1/group_lesson_create";
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
}
