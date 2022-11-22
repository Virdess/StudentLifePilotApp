package kz.studentlife.studenlifepilotapp.UserHTTP;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
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

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.JWT.TokenManager;

public class UserHTTP {
    public String tokenToProvide;
    public  String userid;
    public void sendJsonPostRequest(Context MainActivity, String domain, String login, String password){

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity);
        JSONObject object = new JSONObject();
        try {

            object.put("username", login);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JWTDecode jwtDecode = new JWTDecode();

        // Enter the correct url for your api service site
        String url = "http://192.168.56.1:8081/api/v1" + domain;
        TokenManager tokenManager = new TokenManager(MainActivity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                                try {
                                    JWTDecode.decodeJWT(response.getString("token"));
                                    tokenToProvide = response.getString("token");
                                    tokenManager.CreateLoginSession("username", login);
                                    JSONObject payload = new JSONObject(jwtDecode.payload);
                                    System.out.println(payload.getString("role") + "_____ROLE");
                                    if (payload.getString("role").equals("ROLE_STUDENT")){
                                        Intent intent = new Intent("android.intent.action.MainPage");
                                        MainActivity.startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent("android.intent.action.TeacherMainPage");
                                        MainActivity.startActivity(intent);
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity, "Попытка входа не удалась: что-то пошло не так: " + error, Toast.LENGTH_LONG).show();

                        System.out.println(error);

            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    public void CreateUserHTTP(JSONObject regData, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);



        JWTDecode jwtDecode = new JWTDecode();

        // Enter the correct url for your api service site
        String url = "http://192.168.1.4:8081/api/v1/signup_prof";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, regData,
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

    public void GetUserIDHTTP(String username, Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.4:8081/api/v1/user/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getID = new JSONObject(response);
                            userid = getID.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
            }
        });

        queue.add(groupGet);
    }


    public void GetProfInfo(String username, Context context, TextView userNameView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String baseUrl = "http://192.168.1.4:8081/api/v1/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl + "user/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject userId = new JSONObject(response);



                            StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl + "user_prof/" + userId.getString("id"),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            System.out.println(response);
                                            try {
                                                JSONObject profNames = new JSONObject(response);
                                                byte bytes[] = profNames.getString("firstName").getBytes("ISO-8859-1");
                                                byte bytes2[] = profNames.getString("midName").getBytes("ISO-8859-1");
                                                userNameView.setText(new String(bytes, "UTF-8") + " " + new String(bytes2, "UTF-8"));
                                                userNameView.setVisibility(View.VISIBLE);
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
        queue.add(stringRequest);
    }

    public void Call(String username, Context context, TextView groupView){
        RequestQueue queue = Volley.newRequestQueue(context);
        String baseUrl = "http://192.168.1.4:8081/api/v1/user/" + username;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                System.out.println(error);
            }
        });
        StringRequest groupReq = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject groupGet = new JSONObject(response);
                            System.out.println(groupGet.getString("id") + "_________" + groupGet);
                            GetGroup(groupGet.getString("id"), context, groupView);
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
        queue.add(stringRequest);
        queue.add(groupReq);

    }

    public void GetGroup(String userID, Context context, TextView groupView){
        RequestQueue queue = Volley.newRequestQueue(context);
        String baseUrl = "http://192.168.1.4:8081/api/v1/user_groups_get/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            System.out.println(arr);
                            if (arr != null) {
                                for (int i = 0; i < arr.length(); i++) {
                                    if (arr.optJSONObject(i).getString("userID").equals(userID)) {
                                        System.out.println("GOT!  ___" + arr.getJSONObject(i));
                                        String groupID = arr.getJSONObject(i).getString("groupStudentID");
                                        GetGroupName(groupID, context, groupView);

                                    }
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
        queue.add(stringRequest);
    }

    public void GetGroupName(String groupID, Context context, TextView groupText){
        RequestQueue queue = Volley.newRequestQueue(context);
        String baseUrl = "http://192.168.1.4:8081/api/v1/group_get_id/" + groupID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject resp = new JSONObject(response);
                            byte bytes[] = resp.getString("name").getBytes("ISO-8859-1");
                            groupText.setText(new String(bytes, "UTF-8"));
                            groupText.setVisibility(View.VISIBLE);
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
