package kz.studentlife.studenlifepilotapp.TimeTable;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetTimetableHTTP {
    public static String userid;

    //todo разбить на методы и добавить асинхронность
    //Так как я не понял, как работает асинхронность в джаве (я с джаваскрипта сюда притопал), сделал это чудовище
    public void getTimetableHTTP(String username, Context context, View view){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.2:8081/api/v1/user/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getID = new JSONObject(response);
                            userid = getID.getString("id");
                            System.out.println(userid + "____USER ID TIMETABLE");

                            RequestQueue queue = Volley.newRequestQueue(context);
                            StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.2:8081/api/v1/user_groups_get/",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            System.out.println(response + "____GROUPS TIMETABLE");
                                            try {
                                                JSONObject groupsList = new JSONObject(response);
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
