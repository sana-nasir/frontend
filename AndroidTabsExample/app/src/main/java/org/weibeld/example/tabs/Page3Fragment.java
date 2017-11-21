package org.weibeld.example.tabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.R;

import java.util.ArrayList;

/* Fragment used as page 3 */
public class Page3Fragment extends Fragment {
    RecyclerView notificationView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Notification> arrayList = new ArrayList<>();

    private Button reveal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page3, container, false);
        notificationView = (RecyclerView)rootView.findViewById(R.id.notificationView);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        notificationView.setLayoutManager(layoutManager);
        notificationView.setHasFixedSize(true);
        reveal = (Button) rootView.findViewById(R.id.reveal);

        reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new NotificationAdapter(backgroundTask(), getActivity().getApplicationContext());
                notificationView.setAdapter(adapter);
            }
        });
        return rootView;
    }

    private ArrayList<Notification> backgroundTask(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://real-time-solutions.com:4000/api/notification/3",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while (count < response.length()){
                            JSONObject jsonObject = null;
                            try{
                                jsonObject = response.getJSONObject(count);
                                Notification n = new Notification(jsonObject.getString("title"), jsonObject.getString("alert"));
                                arrayList.add(n);
                                count ++;
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                } ,new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                });
        Mysingleton.getInstance(getActivity().getApplicationContext()).addToRequestque(jsonArrayRequest);
        return arrayList;
    }
}
