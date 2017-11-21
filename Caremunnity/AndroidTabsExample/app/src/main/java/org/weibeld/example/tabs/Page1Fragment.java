package org.weibeld.example.tabs;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.R;

import java.util.HashMap;
import java.util.Map;

/* Fragment used as page 1 */
public class Page1Fragment extends Fragment {
    Button logout;
    public AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);
        logout = (Button)rootView.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://real-time-solutions.com:4000/api/logout",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if(Boolean.parseBoolean(jsonObject.getString("success")) == true) {
                                        Toast.makeText(getActivity().getApplicationContext(), "success", Toast.LENGTH_SHORT);
                                        Intent logOut = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                        startActivity(logOut);
                                    } else {
                                        builder.setTitle("Ops...");
                                        builder.setMessage(jsonObject.getString("message"));
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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", String.valueOf(LoginActivity.user.getId()));

                        return params;
                    }
                };
                Mysingleton.getInstance(getActivity().getApplicationContext()).addToRequestque(stringRequest);
            }
        });
        return rootView;
    }

    public void displayAlert(final String code) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
