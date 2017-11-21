package org.weibeld.example.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public Button loginBtn;
    public EditText username;
    public EditText password;
    private String Username;
    private String Password;
    public AlertDialog.Builder builder;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.userpassword);
        builder = new AlertDialog.Builder(LoginActivity.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Password = password.getText().toString();
                Username = username.getText().toString();
                if(Password.equals("") || Username.equals("")) {
                    builder.setTitle("Ops...");
                    builder.setMessage("Please fill in username and password.");
                    displayAlert("input_error");
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://real-time-solutions.com:4000/api/login",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        if(Boolean.parseBoolean(jsonObject.getString("success")) == true) {
                                            user = new User(Integer.parseInt(jsonObject.getString("user_id")),
                                                    jsonObject.getString("first"), jsonObject.getString("last"),
                                                    jsonObject.getString("email"), jsonObject.getString("username"),
                                                    jsonObject.getString("password"), jsonObject.getString("picture"),
                                                    LoginActivity.this);
                                            Intent loggedin = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(loggedin);
                                        } else {
                                            builder.setTitle("Ops...");
                                            builder.setMessage(jsonObject.getString("message"));
                                            displayAlert("input_error");
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
                            params.put("username", Username);
                            params.put("password", Password);

                            return params;
                        }
                    };
                    Mysingleton.getInstance(LoginActivity.this).addToRequestque(stringRequest);
                }
            }
        });
    }

    public void displayAlert(final String code) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(code.equals("success")) {
                    finish();
                } else if(code.equals("fail")) {
                    password.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
