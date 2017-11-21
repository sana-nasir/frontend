package org.weibeld.example.tabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class User {
    private int id;
    private String first;
    private String last;
    private String username;
    private String email;
    private String password;
    private Bitmap picture;
    Context context;

    public User (int id, String first, String last, String email, String username, String password, String photo, Context context){
        setId(id);
        setEmail(email);
        setPhoto(photo, context);
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setId(int x) {
        id = x;
    }

    public void setEmail(String x) {
        email = x;
    }

    public void setPhoto(String x, Context context) {
        ImageRequest imageRequest = new ImageRequest(x,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        picture = response;
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Mysingleton.getInstance(context).addToRequestque(imageRequest);
    }

}
