package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.ArrayList;

import kz.studentlife.studenlifepilotapp.Group.GroupHTTP;
import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.UserHTTP.UserHTTP;

public class RegisterPage3 extends AppCompatActivity {

    JSONArray resToGroups;
    ArrayList<String> list = new ArrayList<String>();
    String[] groups = {};
    Spinner spinner;
    TextView userProfCreate;
    UserHTTP userHTTP = new UserHTTP();
    String userid;
    GroupHTTP groupHTTP = new GroupHTTP();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page3);
        spinner = findViewById(R.id.spinner);
        userProfCreate = findViewById(R.id.userProfCreate);
        RegisterPage2 registerPage2 = new RegisterPage2();

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);


        userProfCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = spinner.getSelectedItem().toString();
                try {
                    userHTTP.GetUserIDHTTP(RegisterPage2.regData.getString("username"), RegisterPage3.this);
                    userid = userHTTP.userid;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CreateUserGroup(groupName);
                registerPage2.finish();
                finish();
            }
        });



        groupHTTP.GroupsInSpinner(spinner, list, adapter, RegisterPage3.this, "groups");




    }
    private void CreateUserGroup(String groupName){
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject userGroupObj = new JSONObject();
        StringRequest groupGet = new StringRequest(Request.Method.GET, "http://188.130.234.67:8081/api/v1/group_get/" + groupName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getGroupID = new JSONObject(response);
                            userGroupObj.put("groupStudentID", getGroupID.getString("id"));
                            userGroupObj.put("userID", userHTTP.userid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url = "http://188.130.234.67:8081/api/v1/user_group_create";
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userGroupObj,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        queue.add(jsonObjectRequest);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                System.out.println(error);
            }
        });

        queue.add(groupGet);



        JWTDecode jwtDecode = new JWTDecode();

        // Enter the correct url for your api service site

    }


}