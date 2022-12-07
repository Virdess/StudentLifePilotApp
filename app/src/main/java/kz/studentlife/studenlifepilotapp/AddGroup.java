package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;

public class AddGroup extends AppCompatActivity {
    FloatingActionButton turnBackBtnAG;
    TextView groupCodeCreateInput;
    Button groupAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        turnBackBtnAG = findViewById(R.id.turnBackBtnAG);
        groupAddBtn = findViewById(R.id.groupAddBtn);
        groupCodeCreateInput = findViewById(R.id.groupCodeCreateInput);

        turnBackBtnAG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        groupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupCode = groupCodeCreateInput.getText().toString();
                System.out.println(groupCode);
                try {
                    CreateGroupHTTP(groupCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void CreateGroupHTTP(String group) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);



        JWTDecode jwtDecode = new JWTDecode();
        JSONObject groupJSON = new JSONObject();
        groupJSON.put("name",group);

        // Enter the correct url for your api service site
        String url = "http://188.130.234.67:8081/api/v1/group_create";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, groupJSON,
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