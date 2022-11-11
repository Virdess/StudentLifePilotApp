package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
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
import java.util.ArrayList;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;

public class RegisterPage3 extends AppCompatActivity {

    JSONArray resToGroups;
    ArrayList<String> list = new ArrayList<String>();
    String[] groups = {};
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page3);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);








        //Получение списка групп и запись их по именам в выпадающий список
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest groupGet = new StringRequest(Request.Method.GET, "http://192.168.1.2:8081/api/v1/groups",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);
                        try {
                            resToGroups = new JSONArray(response);
                            System.out.println(resToGroups.optJSONObject(0) + " ------- " + resToGroups.optJSONArray(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //строка, необходимая для перекодировки приходящего response
                        String spinnr;
                        if (resToGroups != null) {
                            for(int i = 0; i < resToGroups.length(); i++){
                                try {

                                    //Такую перекодировку нужно делать во всех гет и пост запросах
                                    byte res[] = resToGroups.getJSONObject(i).getString("name").getBytes("ISO-8859-1");
                                    spinnr = new String(res, "UTF-8");
                                    //иначе вылезет кракозябра...

                                    list.add(spinnr);
                                    System.out.println(list);
                                    groups = new String[i];
                                    list.toArray(groups);


                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
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