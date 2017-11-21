package org.weibeld.example.tabs;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.R;

import java.util.ArrayList;

/* Fragment used as page 4 */
public class Page4Fragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<friend> arrayList = new ArrayList<>();

    private Button searchBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page4, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchBtn = (Button) rootView.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                adapter = new RecyclerAdapter(backgroundTask(), getActivity().getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

        });
        return rootView;
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
                                friend f = new friend(Integer.parseInt(jsonObject.getString("user_id").toString()),
                                        jsonObject.getString("first"), jsonObject.getString("last"),
                                        jsonObject.getString("email"), jsonObject.getString("picture"),
                                        getActivity().getApplicationContext());
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
        Mysingleton.getInstance(getActivity().getApplicationContext()).addToRequestque(jsonRequest);
        return arrayList;
    }
}
