package com.example.nick.caremunnity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    final ArrayList<friend> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter(backgroundTask(), this);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<friend> backgroundTask() {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, "http://real-time-solutions.com:4000/api/friends/1",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count < response.length()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = response.getJSONObject(count);
                                friend f = new friend();
                                f.setId(Integer.parseInt(jsonObject.getString("user_id")));
                                f.setName(jsonObject.getString("first"), jsonObject.getString("last"));
                                f.setEmail(jsonObject.getString("email"));
                                f.setPhoto(jsonObject.getString("picture"), MainActivity.this);
                                arrayList.add(f);
                                count ++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Mysingleton.getInstance(MainActivity.this).addToRequestque(jsonRequest);
        return arrayList;
    }
}
