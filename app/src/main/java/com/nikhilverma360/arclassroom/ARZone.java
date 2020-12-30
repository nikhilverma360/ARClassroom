package com.nikhilverma360.arclassroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Iterator;

public class ARZone extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ARZoneAdapter mARZoneAdapter;
    private ArrayList<ARZoneModel> arZoneModelList;
    private RequestQueue mRequestQueue;

    private TextView classname,section,room,subject,classcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r_zone);

        classname= findViewById(R.id.arclassname);
        section=findViewById(R.id.arclasssection);
        room=findViewById(R.id.arclassroomnum);
        subject= findViewById(R.id.arclasssubject);
        classcode= findViewById(R.id.arclasscode);
        Intent i = getIntent();

        classname.setText(i.getStringExtra("classname"));
        section.setText(i.getStringExtra("section"));
        room.setText(i.getStringExtra("room"));
        subject.setText(i.getStringExtra("subject"));
        classcode.setText("Class Code: "+i.getStringExtra("classcode"));

        mRecyclerView = findViewById(R.id.arobjectrecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        arZoneModelList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        //parseJSON();
        parseJSONTest();
    }

    private void parseJSONTest() {
        String url = "https://console.echoar.xyz/query?key=withered-shape-6701";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject db = response.getJSONObject("db");
                            Iterator<String> keys = db.keys();
                            while (keys.hasNext()){
                                String key = keys.next();
                                if (db.get(key) instanceof JSONObject){
                                    JSONObject objects = (JSONObject) db.get(key);
                                    JSONObject additionalData = objects.getJSONObject("additionalData");
                                    String shortURL = additionalData.getString("shortURL");
                                    String title = additionalData.getString("title");
                                    String description = additionalData.getString("description");
                                    arZoneModelList.add(new ARZoneModel(title, description, shortURL));

                                    Log.d("tatti", "onResponse: "+shortURL + " " + title +" "+ description);
                                }
                                mARZoneAdapter = new ARZoneAdapter(ARZone.this, arZoneModelList);
                                mRecyclerView.setAdapter(mARZoneAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
}